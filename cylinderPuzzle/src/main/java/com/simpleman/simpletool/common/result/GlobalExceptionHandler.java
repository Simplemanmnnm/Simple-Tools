package com.simpleman.simpletool.common.result;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 其他exception异常
     *
     * @param exception 异常详情
     * @return 格式化后的异常结果
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleException(Exception exception, HttpServletRequest request) {
        LogManager.getLogger().error("the exception is {}", exception);
        return Result.fail(ResultCode.FAILURE, "internal service error");
    }

    /**
     * 捕获参数校验异常错误并进行兜底处理
     *
     * @param ex post请求体中的通过注解校验参数失败时，抛出的异常
     * @return Result 响应结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        LogManager.getLogger().error("the exception is {}", ex);

        return Result.fail(ResultCode.FAILURE, ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";")));
    }
}
