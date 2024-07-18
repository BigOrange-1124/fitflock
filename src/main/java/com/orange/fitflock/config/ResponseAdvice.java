package com.orange.fitflock.config;

import com.orange.fitflock.common.OrangeException;
import com.orange.fitflock.common.Result;
import com.orange.fitflock.common.enums.BusinessCode;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collection;

/**
 * @title: ResponseAdvice
 * @author: 郭皓隆
 * @date: 2023-08-28
 * @Description: 统一处理响应体
 */
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    private static final String ERROR = "error";

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (returnType.toString().contains(ERROR)) {
            return body;
        }
        if (body instanceof String) {
            return body;
        }
        if (body instanceof Result) {
            return body;
        }
        if (body instanceof Boolean) {
            boolean boolValue = (Boolean) body;
            return boolValue ? Result.success() : Result.fail();
        }
        if (body instanceof Collection) {
            Collection<?> collection = (Collection<?>) body;
            if (collection.isEmpty()) {
                return Result.fail(BusinessCode.EMPTY);
            }
            return Result.success(collection);
        }

        return Result.success(body);
    }

    @ExceptionHandler(OrangeException.class)
    public Result orangeException(OrangeException e) {
        return Result.fail(e.getCode(),e.getMsg());
    }
}
