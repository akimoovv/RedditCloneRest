package com.springboot.reditclone.demo.exceptions;

import org.springframework.http.HttpStatus;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringRedditException(String exMessage, HttpStatus unauthorized) {
        super(exMessage);
    }

    public SpringRedditException(String s) {
        super(s);
    }
}
