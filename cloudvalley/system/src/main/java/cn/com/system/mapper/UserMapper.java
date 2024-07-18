package cn.com.system.mapper;

import cn.com.core.post.vo.UserVO;
import cn.com.core.system.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户信息表，包含用户基本信息 Mapper 接口
 * </p>
 *
 * @author dashengz
 * @since 2024-02-07
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<UserVO> getUserInfoList(List<String> userIdList);
}
