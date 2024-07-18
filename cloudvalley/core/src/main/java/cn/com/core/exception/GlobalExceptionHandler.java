package cn.com.core.exception;

import cn.com.core.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 参数校验通用异常处理
 *
 * @author tangsm
 * https://blog.csdn.net/tangyb828/article/details/126884417
 */
@Order(0)
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * BindException异常处理
     * <p>BindException: 作用于@Validated @Valid 注解，仅对于表单提交有效，对于以json格式提交将会失效</p>
     *
     * @param e BindException异常信息
     * @return 响应数据
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(BindException e) {
        String msg = e.getBindingResult().getFieldErrors()
                .stream()
                .map(n -> String.format("%s: %s", n.getField(), n.getDefaultMessage()))
                .reduce((x, y) -> String.format("%s; %s", x, y))
                .orElse("参数输入有误");

        logger.error("BindException异常，参数校验异常：{}", msg);
        return Result.error(msg);
    }

    /**
     * MethodArgumentNotValidException-Spring封装的参数验证异常处理
     * <p>MethodArgumentNotValidException：作用于 @Validated @Valid 注解，接收参数加上@RequestBody注解（json格式）才会有这种异常。</p>
     *
     * @param e MethodArgumentNotValidException异常信息
     * @return 响应数据
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors()
                .stream()
                .map(n -> String.format("%s: %s", n.getField(), n.getDefaultMessage()))
                .reduce((x, y) -> String.format("%s; %s", x, y))
                .orElse("参数输入有误");
        logger.error("MethodArgumentNotValidException异常，参数校验异常：{}", msg);
        return Result.error(msg);
    }

    /**
     * ConstraintViolationException-jsr规范中的验证异常，嵌套检验问题
     * <p>ConstraintViolationException：作用于 @NotBlank @NotNull @NotEmpty 注解，校验单个String、Integer、Collection等参数异常处理。</p>
     * <p>注：Controller类上必须添加@Validated注解，否则接口单个参数校验无效</p>
     *
     * @param e ConstraintViolationException异常信息
     * @return 响应数据
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result constraintViolationExceptionHandler(ConstraintViolationException e) {
        String msg = e.getMessage();
        logger.error("ConstraintViolationException，参数校验异常：{}", msg);
        return Result.error(msg);
    }

    /**
     * 捕获自定义异常
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody//返回json串
    public Result customError(CustomException e) {
        logger.error("CustomException，自定义异常：{}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    public Result handleException(Exception e) {
//        String msg = e.getMessage();
//        logger.error("Exception.{}：", msg);
//        return Result.error(msg);
//    }
}
