package com.lc.test.spring.common;

import com.lc.test.common.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author wlc
 * @description
 * @date 2021/1/18 0018 16:49
 */
@Slf4j
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class ControllerHandler {
    /**
     * Validation校验@RequestParam抛出的参数验证异常，会进行aop日志打印
     * @param exception ConstraintViolationException
     * @return 参数验证异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseResult<String> constraintViolationException(ConstraintViolationException exception) {
        String errorMsg = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining());
        log.error("参数校验ConstraintViolationException 异常信息:{}",errorMsg);
        return ResponseResult.createByErrorMessage(errorMsg);
    }

    /**
     * Validation校验@RequestBody抛出的参数验证异常，如果使用aop拦截controller，这个异常不会进入到aop日志处理
     * @param exception MethodArgumentNotValidException
     * @return 参数验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseResult<String> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String errorMsg = Optional.ofNullable(exception.getBindingResult().getFieldError())
                .map(FieldError::getDefaultMessage)
                .orElse("");
        log.error("参数校验MethodArgumentNotValidException异常,错误信息:{}",errorMsg);
        return ResponseResult.createByErrorMessage(errorMsg);
    }
}
