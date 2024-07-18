package cn.com.post.service.impl;

import cn.com.core.feign.IFeignSystemController;
import cn.com.core.post.dto.PostQueryDTO;
import cn.com.core.post.dto.PostTagNameDTO;
import cn.com.core.post.dto.UserStatusOnPostDTO;
import cn.com.core.post.po.*;
import cn.com.core.post.vo.PostAdminVO;
import cn.com.core.post.vo.PostVO;
import cn.com.core.post.vo.UserVO;
import cn.com.core.system.po.User;
import cn.com.core.util.AuthUtil;
import cn.com.core.util.Constant;
import cn.com.core.util.JwtUser;
import cn.com.core.util.Result;
import cn.com.post.mapper.PostMapper;
import cn.com.post.service.*;
import cn.com.post.util.wordutil.SensitiveWordUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 帖子表 服务实现类
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-04
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PostMapper postMapper;

    @Resource
    private ILikeService likeService;

    @Resource
    private IFavoriteService favoriteService;

    @Resource
    private ITagService tagService;

    @Resource
    private IPostTagService postTagService;
    @Resource
    private ICategoryService categoryService;
    @Resource
    private IFeignSystemController feignSystemController;

    @Resource
    private SensitiveWordUtil sensitiveWordUtil;

    @Resource
    private IFollowService followService;

    @Override
    public boolean add(Post post) {
        post.setUserId(AuthUtil.getUserInfo().getId());
        boolean flag = save(post);
        // 获取帖子的标签数据，若不为空则插入数据到 post_tag 表中
        List<String> tagIdList = post.getTagIdList();
        if (tagIdList != null && tagIdList.size() != 0) {
            postTagService.add(post.getId(), tagIdList);
        }
        return flag;
    }

    @Override
    public boolean deleteById(String id) {
        return postMapper.deleteById(id) > 0;

    }

    @Override
    public boolean deleteBatch(List<String> ids) {
        return postMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public boolean update(Post post) {
        // 获取帖子的标签数据，若不为空则更新数据到 post_tag 表中
        List<String> tagIdList = post.getTagIdList();
        if (tagIdList != null && tagIdList.size() != 0) {
            postTagService.update(post.getId(), tagIdList);
        }
        return updateById(post);
    }

    @Override
    public Result selectById(String id) {
        Post post = postMapper.selectById(id);
        if (post == null) {
            return Result.error(Constant.NOT_EXIST);
        }
        update(new LambdaUpdateWrapper<Post>()
                .set(Post::getViews, post.getViews() + 1)
                .eq(Post::getId, id));
        // 敏感词处理
        post.setTitle(sensitiveWordUtil.replace(post.getTitle()));
        post.setDescription(sensitiveWordUtil.replace(post.getDescription()));
        post.setContent(sensitiveWordUtil.replace(post.getContent()));
        // 转化成postVO对象
        PostVO postVO = BeanUtil.copyProperties(post, PostVO.class);
        // 设置帖子的标签信息
        postVO.setTagNameList(tagService.getPostTagInfo(id));
        postVO.setTagIdList(tagService.getPostTagIdList(id));
        // 设置作者信息
        User user = feignSystemController.getUserById(postVO.getUserId());
        postVO.setNickname(user.getNickname());
        postVO.setAvatar(user.getAvatar());
        // 获取当前登录用户
        JwtUser userInfo = AuthUtil.getUserInfo();
        if (userInfo != null) {
            // 当前用户是否点赞
            Like userLike = likeService.getCurrentUserLike(id);
            postVO.setUserLike(userLike != null);
            // 当前用户是否收藏
            Favorite userFavorite = favoriteService.getCurrentUserFavorite(id);
            postVO.setUserFavorite(userFavorite != null);
            // 当前用户是否关注作者
            postVO.setUserFollow(followService.isFollowed(userInfo.getId(), postVO.getUserId()));
        }
        Map<String, Long> statisticsMap = baseMapper.queryAuthorStatistics(postVO.getUserId());
        postVO.setPosts(statisticsMap.get("posts"));
        postVO.setFollows(statisticsMap.get("follows"));
        postVO.setFans(statisticsMap.get("fans"));
        return Result.ok(postVO);
    }

    @Override
    public IPage<PostVO> page(PostQueryDTO queryDTO) {
        // 执行分页查询
        IPage<Post> postPage = getBaseMapper().page(queryDTO.getPage(), queryDTO);
        List<Post> postRecords = postPage.getRecords();
        if (postRecords.size() == 0) {
            return new Page<>(queryDTO.getCurrent(), queryDTO.getSize(), postPage.getTotal());
        }
        // 敏感词处理
        postRecords.forEach(post -> {
            post.setTitle(sensitiveWordUtil.replace(post.getTitle()));
            post.setDescription(sensitiveWordUtil.replace(post.getDescription()));
        });
        // 推荐功能，若请求参数的type字段值等于recommend，则文章列表按照文章浏览量、点赞数、收藏数的总和降序排序
        if ("recommend".equals(queryDTO.getType())) {
            postRecords = postRecords.stream()
                    .sorted((b1, b2) -> {
                        Integer sum1 = b1.getViews() + b1.getLikes() + b1.getFavorites();
                        Integer sum2 = b2.getViews() + b2.getLikes() + b2.getFavorites();
                        return sum2.compareTo(sum1);
                    })
                    .collect(Collectors.toList()); // 收集排序后的结果到新的列表中
            postPage.setRecords(postRecords); // 更新 postPage 的记录列表
        }
        return getPostVOPage(postPage);
    }

    /**
     * post数据集合转换成postVO数据集合
     */
    private Page<PostVO> getPostVOPage(IPage<Post> postPage) {
        List<PostVO> postVOList = BeanUtil.copyToList(postPage.getRecords(), PostVO.class);

        // 构建userMap，键是用户id，值是用户名name
        List<String> userIdList = postVOList.stream()
                .map(PostVO::getUserId)
                .collect(Collectors.toList());
        List<UserVO> userInfoList = feignSystemController.getUserInfoList(userIdList);
        Map<String, String> userInfoMap = userInfoList.stream()
                .collect(Collectors
                        .toMap(UserVO::getId, UserVO::getNickname)
                );
        List<String> postIdList = postVOList.stream()
                .map(PostVO::getId)
                .collect(Collectors.toList());
        // 构建postTagNameMap，键是帖子id，值是标签名
        List<PostTagNameDTO> postTagNameList = tagService.selectTagNameByPostIds(postIdList);
        Map<String, String> postTagNameMap = postTagNameList.stream()
                .collect(Collectors.toMap(PostTagNameDTO::getPostId, PostTagNameDTO::getTagNames));

        Map<String, Boolean> userLikeMap = null;
        Map<String, Boolean> userFavoriteMap = null;
        JwtUser userInfo = AuthUtil.getUserInfo();
        if (userInfo != null) {
            // 构建userLikeMap，键是帖子id，值是布尔值，表示当前登录用户是否点赞帖子
            List<UserStatusOnPostDTO> userLikeList = likeService.selectIsLikedByUserIdAndPostIds(userInfo.getId(), postIdList);
            userLikeMap = userLikeList.stream()
                    .collect(Collectors.toMap(UserStatusOnPostDTO::getPostId, UserStatusOnPostDTO::getStatus));
            // 构建userFavoriteMap，键是帖子id，值是布尔值，表示当前登录用户是否收藏帖子
            List<UserStatusOnPostDTO> userFavoriteList = favoriteService.selectIsFavoritedByUserIdAndPostIds(userInfo.getId(), postIdList);
            userFavoriteMap = userFavoriteList.stream()
                    .collect(Collectors.toMap(UserStatusOnPostDTO::getPostId, UserStatusOnPostDTO::getStatus));
        }

        // 处理postVO数据集合
        for (PostVO postVO : postVOList) {
            String userId = postVO.getUserId();
            String postId = postVO.getId();
            postVO.setNickname(userInfoMap.get(userId));
            postVO.setTagNameList(postTagNameMap.get(postId));
            postVO.setUserLike(userLikeMap != null && userLikeMap.get(postId));
            postVO.setUserFavorite(userFavoriteMap != null && userFavoriteMap.get(postId));
        }

        // 创建新的Page<PostVO>对象
        Page<PostVO> postVOPage = new Page<>(postPage.getCurrent(), postPage.getSize(), postPage.getTotal());
        // 设置新的Page<PostVO>对象的记录列表
        postVOPage.setRecords(postVOList);
        return postVOPage;
    }

    @Override
    public IPage<PostVO> userPage(PostQueryDTO queryDTO) {
        JwtUser userInfo = AuthUtil.getUserInfo();
        // 查询用户点赞、收藏、发布的帖子时，需要获取用户id
        if (userInfo != null && !StringUtils.isEmpty(queryDTO.getType())) {
            queryDTO.setUserId(userInfo.getId());
        }
        // 执行分页查询
        IPage<Post> postPage = getBaseMapper().userPage(queryDTO.getPage(), queryDTO);
        if (postPage.getRecords().size() == 0) {
            return new Page<>(queryDTO.getCurrent(), queryDTO.getSize(), postPage.getTotal());
        }
        // 敏感词处理
        postPage.getRecords().forEach(post -> {
            post.setTitle(sensitiveWordUtil.replace(post.getTitle()));
            post.setDescription(sensitiveWordUtil.replace(post.getDescription()));
        });
        return getPostVOPage(postPage);
    }

    @Override
    public Result forbiddenPost(String postId) {
        logger.debug("PostServiceImpl.forbiddenPost:{}", postId);
        boolean flag = update(new LambdaUpdateWrapper<Post>()
                .set(Post::getIsLock, 1)
                .eq(Post::getId, postId)
        );
        return flag ? Result.ok(Constant.RESULT_FORBIDDEN_OK) : Result.error(Constant.RESULT_FORBIDDEN_FAIL);
    }

    @Override
    public Result startPost(String postId) {
        logger.debug("PostServiceImpl.startPost:{}", postId);
        boolean flag = update(new LambdaUpdateWrapper<Post>()
                .set(Post::getIsLock, 0)
                .eq(Post::getId, postId)
        );
        return flag ? Result.ok(Constant.RESULT_START_OK) : Result.error(Constant.RESULT_START_FAIL);
    }

    @Override
    public Result getPostList(PostQueryDTO queryDTO) {
        logger.debug("PostServiceImpl.getPostList:{}", queryDTO);
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(queryDTO.getTitle() != null, Post::getTitle, queryDTO.getTitle())
                .eq(queryDTO.getCategoryId() != null, Post::getCategoryId, queryDTO.getCategoryId())
                .le(queryDTO.getStartTime() != null, Post::getCreateTime, queryDTO.getStartTime())
                .ge(queryDTO.getEndTime() != null, Post::getCreateTime, queryDTO.getEndTime())
                .orderByDesc(Post::getCreateTime);
        IPage<Post> page = page(queryDTO.getPage(), queryWrapper);
        List<PostAdminVO> postAdminVOList = page.getRecords()
                .stream()
                .map(post -> {
                    User user = feignSystemController.getUserById(post.getUserId());
                    Category category = categoryService.getById(post.getCategoryId());
                    List<String> tagIdList = postTagService.list(new LambdaQueryWrapper<PostTag>().eq(PostTag::getPostId, post.getId()))
                            .stream()
                            .map(PostTag::getTagId)
                            .collect(Collectors.toList());
                    String tagNameList = tagService.list(new LambdaQueryWrapper<Tag>()
                                    .in(!tagIdList.isEmpty(), Tag::getId, tagIdList))
                            .stream()
                            .map(Tag::getName)
                            .collect(Collectors.joining(","));
                    return PostAdminVO.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .cover(post.getCover())
                            .description(post.getDescription())
                            .content(post.getContent())
                            .idLocked(post.getIsLock())
                            .category(category)
                            .author(user != null ? UserVO.from(user) : null)
                            .tagNameList(tagNameList)
                            .createTime(post.getCreateTime())
                            .updateTime(post.getUpdateTime())
                            .build();
                }).collect(Collectors.toList());
        IPage<PostAdminVO> postAdminVOIPage = new Page<PostAdminVO>()
                .setPages(page.getPages())
                .setCurrent(page.getCurrent())
                .setSize(page.getSize())
                .setTotal(page.getTotal())
                .setRecords(postAdminVOList);
        return Result.ok(postAdminVOIPage);
    }

}
