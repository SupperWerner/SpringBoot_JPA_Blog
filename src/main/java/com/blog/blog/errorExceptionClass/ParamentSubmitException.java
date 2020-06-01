package com.blog.blog.errorExceptionClass;

public class ParamentSubmitException extends RuntimeException {

    public ParamentSubmitException(String message) {
        super(message);
    }

    public ParamentSubmitException(String message, Throwable cause) {
        super(message, cause);
    }
}
