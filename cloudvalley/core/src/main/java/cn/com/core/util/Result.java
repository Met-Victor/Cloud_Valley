package cn.com.core.util;

import cn.com.core.enums.ResultEnum;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @className: Result
 * @author: Met.
 * @date: 2024/2/7
 **/
@Data
@NoArgsConstructor // 无参构造方法
public final class Result implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Result.class);

    private static final long serialVersionUID = 1L;

    /**
     * 响应业务状态码
     */
    private Integer code;
    /**
     * 响应是否成功
     */
    @JsonIgnore
    private boolean success;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应中的数据
     */
    private Object data = null;

    private Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        if (data != null) {
            this.data = data;
        }
        success = code == ResultEnum.SUCCESS.getCode();
    }

    public static Result ok() {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), null);
    }

    public static Result ok(Object data) {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), data);
    }

    public static Result ok(String message, Object data) {
        return new Result(ResultEnum.SUCCESS.getCode(), message, data);
    }

    public static Result error(String message) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        logger.debug("返回错误：code={}, message={}, fileName：{}, method:{}，lineNumber:{}", ResultEnum.ERROR.getCode(), message, stackTrace[2].getFileName(), stackTrace[2].getMethodName(), stackTrace[2].getLineNumber());
        return new Result(ResultEnum.ERROR.getCode(), message, null);
    }

    public static Result error(Integer code, String message) {
        return new Result(code, message, null);
    }

    public static Result error(ResultEnum resultEnum) {
        return new Result(resultEnum.getCode(), resultEnum.getDesc(), null);
    }

    public static Result build(int code, String message) {
        logger.debug("返回结果：code={}, message={}", code, message);
        return new Result(code, message, null);
    }

    public static Result build(ResultEnum resultEnum) {
        logger.debug("返回结果：code={}, message={}", resultEnum.getCode(), resultEnum.getDesc());
        return new Result(resultEnum.getCode(), resultEnum.getDesc(), null);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}