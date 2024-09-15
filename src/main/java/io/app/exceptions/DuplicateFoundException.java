package io.app.exceptions;

public class DuplicateFoundException extends RuntimeException {
    public DuplicateFoundException(String msg) {
        super(msg);
    }
}
