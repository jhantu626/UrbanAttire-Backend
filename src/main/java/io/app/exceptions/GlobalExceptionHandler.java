package io.app.exceptions;

import io.app.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse handleResourceNotFoundException(Exception exception){
        return ApiResponse.builder()
                .msg(exception.getMessage())
                .status(false)
                .build();
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ApiResponse handleInvalidCredentials(Exception exception){
        return ApiResponse.builder()
                .msg(exception.getMessage())
                .status(false)
                .build();
    }

    @ExceptionHandler(DuplicateFoundException.class)
    public ApiResponse handleDuplicateFoundExeption(DuplicateFoundException exception){
        return ApiResponse.builder()
                .msg(exception.getMessage())
                .status(false)
                .build();
    }


    @ExceptionHandler(FileSizeExceededException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ApiResponse handleFileSizeExceededException(Exception ex){
        return new ApiResponse(ex.getMessage(),false);
    }


}
