package com.example.shipgofunding.controller;

import com.example.shipgofunding.config.errors.exception.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class ExceptionTestController {

    @GetMapping("/bad-request")
    public void throwBadRequest() {
        Map<String, String> errors = new HashMap<>();
        errors.put("field1", "Field 1 must not be empty");
        errors.put("field2", "Field 2 must be a valid email");

        // Exception400 예외를 강제로 발생시킵니다.
        throw new Exception400(errors, "Invalid input values");
    }

    @GetMapping("/unauthorized")
    public void throwUnauthorized() {
        // Exception401 예외를 강제로 발생시킵니다.
        throw new Exception401("Unauthorized Error Message");
    }

    @GetMapping("/forbidden")
    public void throwForbidden() {
        // Exception403 예외를 강제로 발생시킵니다.
        throw new Exception403("Forbidden Error Message");
    }

    @GetMapping("/not-found")
    public void throwNotFound() {
        // Exception404 예외를 강제로 발생시킵니다.
        throw new Exception404("Not Found Error Message");
    }

    @GetMapping("/server-error")
    public void throwServerError() {
        // Exception500 예외를 강제로 발생시킵니다.
        throw new Exception500("Server Error Message");
    }

    @GetMapping("/unknown-error")
    public void throwUnknownError() {
        // 일반 Exception을 강제로 발생시킵니다.
        throw new RuntimeException("Unknown Error Message");
    }
}
