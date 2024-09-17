package io.app.exceptions;

public class FileSizeExceededException extends RuntimeException {
    public FileSizeExceededException(String msg) {
        super(msg);
    }
}
