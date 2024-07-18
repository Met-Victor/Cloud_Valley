package cn.com.core.exception;

import cn.com.core.enums.ResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 */

@Getter
@Setter
public class CustomException extends RuntimeException {

    private Integer code;

    public CustomException(ResultEnum resultEnum) {
        super(resultEnum.getDesc());
        this.code = resultEnum.getCode();
    }

    public CustomException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

}
