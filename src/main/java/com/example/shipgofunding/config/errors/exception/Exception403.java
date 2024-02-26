package com.example.shipgofunding.config.errors.exception;

import com.example.shipgofunding.config.utils.ApiResponseBuilder;
import lombok.Getter;

@Getter
public class Exception403 extends RuntimeException {
    public Exception403(String message) {
        super(message);
    }

    public ApiResponseBuilder.ApiResponse<?> body(){
        return ApiResponseBuilder.error(getMessage());
    }
}
