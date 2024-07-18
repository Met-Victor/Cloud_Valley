package cn.com.post.mapper;

import cn.com.core.post.po.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dashengz
 * @since 2024-04-16
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}
