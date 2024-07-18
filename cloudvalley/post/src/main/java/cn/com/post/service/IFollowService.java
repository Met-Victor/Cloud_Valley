package cn.com.post.service;

import cn.com.core.post.po.Follow;
import cn.com.core.post.vo.UserVO;
import cn.com.core.util.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户关注表 服务类
 * </p>
 *
 * @author dashengz
 * @since 2024-03-11
 */
public interface IFollowService extends IService<Follow> {
    /**
     * 添加关注
     *
     * @param noticerId
     * @return {@link Result}
     */
    Result addFollow(String noticerId);

    /**
     * 取消关注
     *
     * @param noticerId
     * @return {@link Result}
     */
    Result cancelFollow(String noticerId);

    /**
     * 获取登录用户关注列表
     *
     * @return {@link Result}
     */
    Result getFollowList();

    /**
     * 查询用户是否关注另一个用户
     *
     * @return {@link Result}
     */
    boolean isFollowed(String userId, String noticerId);

}
