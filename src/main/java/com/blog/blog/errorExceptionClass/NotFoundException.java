package com.blog.blog.errorExceptionClass;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 定义新的异常
 * blog找不到时退直接抛出该异常
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends  RuntimeException{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
