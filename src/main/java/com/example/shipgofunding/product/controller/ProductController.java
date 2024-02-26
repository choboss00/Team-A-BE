package com.example.shipgofunding.product.controller;

import com.example.shipgofunding.config.utils.ApiResponseBuilder;
import com.example.shipgofunding.product.response.ProductResponse;
import com.example.shipgofunding.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/banners/main")
    public ResponseEntity<?> getMainBanners() {
        //TO-DO : 메인 배너 데이터를 조회하는 로직 구현하기
        List<ProductResponse.BannerResponse> banners = productService.getMainBanners();

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(banners));
    }

}
