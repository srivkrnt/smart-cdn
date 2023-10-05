package com.cdn.smartcdn.exceptions;

import org.springframework.http.HttpStatus;

public class ImageNotFoundException extends BaseException {

    public ImageNotFoundException(String message, String ... info) {
        super(message, HttpStatus.NOT_FOUND, info);
    }

    public ImageNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, (String) null);
    }
}