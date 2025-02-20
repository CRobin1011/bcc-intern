package com.ignit.internship.exception;

import org.springframework.security.core.AuthenticationException;

public class IdNotFoundException extends AuthenticationException {

    public IdNotFoundException(String msg) {
        super(msg);
    }

    public IdNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
