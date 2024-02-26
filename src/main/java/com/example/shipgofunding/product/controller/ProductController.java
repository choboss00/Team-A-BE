package com.example.shipgofunding.product.controller;

import com.example.shipgofunding.config.utils.ApiResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

    @GetMapping("/banners/main")
    public ResponseEntity<?> getMainBanners() {
        //TO-DO : 메인 배너 데이터를 조회하는 로직 구현하기
        return ResponseEntity.ok(ApiResponseBuilder.success("메인 배너 조회 성공"));
    }

}
