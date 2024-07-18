package cn.com.auth.service;

/**
 * @className: RedisKeyConstants
 * @author: Met.
 * @date: 2024/3/2
 **/
public interface RedisKeyConstant {
    /**
     * 编辑框，锁定的超时时间（分）
     */
    Long TASK_REPLY_TIME_OUT = 15L;

    /**
     * 编辑框，阈值的超时时间（秒）
     */
    Long TASK_REPLY_THR_TIME_OUT = 180L;

    /**
     * 问题回复里面的KEY
     * %s= taskId + replyId （可为空）
     *
     */
    String TASK_REPLY_KEY = "TASK_REPLY_%s";

    /**
     * 问题回复里面的KEY
     * %s= taskId + replyId （可为空）
     * 这是记录内容的，验证内容是否存在
     *
     */
    String TASK_REPLY_CONTENT_KEY = "TASK_REPLY_CONTENT_%s";

    /**
     * 记录操作时间
     * 长时间没操作
     */
    String TASK_REPLY_TIME_KEY = "TASK_REPLY_TIME_%s";


    /**
     * 用户token JTI的后缀
     */
    String POSTFIX_USER_TOKEN_JTI = "_user_token_jti";
}
