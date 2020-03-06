package com.ori.notebook.common.Exception;

public class NoSuchIdException extends RuntimeException {
    private String errMessage;

    public NoSuchIdException() {
        super();
    }

    public NoSuchIdException(String errMessage) {
        super(errMessage);
    }
}
