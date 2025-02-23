package com.ignit.internship.exception;

public class EmailNotFoundException extends Exception {

    public EmailNotFoundException(String msg) {
        super(msg);
    }

    public EmailNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
