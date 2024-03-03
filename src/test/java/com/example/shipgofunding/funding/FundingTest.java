package com.example.shipgofunding.funding;

import com.example.shipgofunding.config.s3.S3UploadService;
import com.example.shipgofunding.funding.controller.FundingController;
import com.example.shipgofunding.funding.service.FundingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
public class FundingTest {

    private MockMvc mvc;

    @Autowired
    private FundingService fundingService;

    @Autowired
    private S3UploadService s3UploadService;

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(new FundingController(fundingService, s3UploadService)).build();
    }

    @Test
    @DisplayName("랜덤한 4개의 배너 정보를 잘 가져오는지 확인하는 테스트 코드")
    void getBannerTest() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/api/banners/main"));

        // then
        String response = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + response);

        resultActions.andExpect(jsonPath("$.status").value("success"));

    }

    @Test
    @DisplayName("마감임박 랜덤 펀딩 상품 목록 3개를 잘 조회하는지 확인")
    void UrgentFundingTest() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/api/fundings/urgent"));

        // then
        String response = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + response);

        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("인기 상품 6개 조회 테스트 코드")
    void PopularMainPageFundingTest() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/api/fundings/popular")
                .contentType("application/json;charset=UTF-8"));

        // then
        String response = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + response);

        resultActions.andExpect(jsonPath("$.status").value("success"));
    }
    
    @Test
    @DisplayName("펀딩 상품 목록 전체 조회 테스트 코드")
    void FundingTest() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/api/fundings")
                .contentType("application/json;charset=UTF-8"));

        // then
        String response = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + response);

        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("펀딩 상품 목록 페이징 처리 테스트 코드")
    void FundingTestWithPage() throws Exception {
        // given
        int page = 1;

        // when
        ResultActions resultActions = mvc.perform(get("/api/fundings")
                .param("page", String.valueOf(page))
                .contentType("application/json;charset=UTF-8"));

        // then
        String response = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + response);

        resultActions.andExpect(jsonPath("$.status").value("success"));

    }
    
    @Test
    @DisplayName("펀딩 상품 목록 검색 결과 조회 테스트")
    void FundingTestWithSearch() throws Exception {
        // given
        String search = "Product";

        // when
        ResultActions resultActions = mvc.perform(get("/api/fundings")
                .param("search", search)
                .contentType("application/json;charset=UTF-8"));

        // then
        String response = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + response);

        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("펀딩 상품 목록 검색 + 인기순 조회 결과 조회 테스트")
    void FundingTestWithSearchAndSortLikes() throws Exception {
        // given
        String search = "Product";
        String sorted = "인기순";

        // when
        ResultActions resultActions = mvc.perform(get("/api/fundings")
                .param("search", search)
                .param("sorted", sorted)
                .contentType("application/json;charset=UTF-8"));

        // then
        String response = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + response);

        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("펀딩 상품 목록 가격 설정 테스트")
    void FundingTestWithPrice() throws Exception {
        // given
        int minPrice = 1000;
        int maxPrice = 3000;

        // when
        ResultActions resultActions = mvc.perform(get("/api/fundings")
                        .param("min_price", String.valueOf(minPrice))
                        .param("max_price", String.valueOf(maxPrice))
                .contentType("application/json;charset=UTF-8"));

        // then
        String response = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + response);

        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("인기순 펀딩 상품 정렬이 잘 되는지 테스트")
    void FundingTestWithSortFieldLikes() throws Exception {
        // given
        String sorted = "인기순";

        // when
        ResultActions resultActions = mvc.perform(get("/api/fundings")
                .param("sorted", sorted)
                .contentType("application/json;charset=UTF-8"));

        // then
        String response = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + response);

        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("최신순 펀딩 상품 정렬이 잘 되는지 테스트")
    void FundingTestWithSortCreatedAt() throws Exception {
        // given
        String sorted = "최신순";

        // when
        ResultActions resultActions = mvc.perform(get("/api/fundings")
                .param("sorted", sorted)
                .contentType("application/json;charset=UTF-8"));

        // then
        String response = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + response);

        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("마감 임박순 펀딩 상품 정렬이 잘 되는지 테스트")
    void FundingTestWithSortEndDate() throws Exception {
        // given
        String sorted = "마감 임박순";

        // when
        ResultActions resultActions = mvc.perform(get("/api/fundings")
                .param("sorted", sorted)
                .contentType("application/json;charset=UTF-8"));

        // then
        String response = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + response);

        resultActions.andExpect(jsonPath("$.status").value("success"));
    }
    
    @Test
    @DisplayName("카테고리 별 펀딩 상품 목록 조회")
    void FundingTestWithCategory() throws Exception {
        // given
        String category = "테크가전";

        // when
        ResultActions resultActions = mvc.perform(get("/api/fundings")
                        .param("category", category)
                .contentType("application/json;charset=UTF-8"));

        // then
        String response = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + response);

        resultActions.andExpect(jsonPath("$.status").value("success"));
    }
    
    @Test
    @DisplayName("카테고리 + 인기순 정렬 펀딩 상품 목록 조회")
    void FundingTestWithCategoryAndSortLikes() throws Exception {
        // given
        String category = "테크가전";
        String sorted = "인기순";

        // when
        ResultActions resultActions = mvc.perform(get("/api/fundings")
                .param("category", category)
                .param("sorted", sorted)
                .contentType("application/json;charset=UTF-8"));

        // then
        String response = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + response);

        resultActions.andExpect(jsonPath("$.status").value("success"));
    }


    @Test
    @DisplayName("카테고리 + 정렬 조건 ( 인기순 ) + 가격 범위 펀딩 상품 목록 조회")
    void FundingTestCategoryAndSortLikesAndPrice() throws Exception {
        // given
        int minPrice = 1000;
        int maxPrice = 2000;
        String category = "테크가전";
        String sorted = "인기순";

        // when
        ResultActions resultActions = mvc.perform(get("/api/fundings")
                .param("min_price", String.valueOf(minPrice))
                .param("max_price", String.valueOf(maxPrice))
                .param("category", category)
                .param("sorted", sorted)
                .contentType("application/json;charset=UTF-8"));

        // then
        String response = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + response);

        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("펀딩 상세 페이지 조회 테스트")
    void FundingDetailGetTest() throws Exception {
        // given
        int id = 14;

        // when
        ResultActions resultActions = mvc.perform(get("/api/fundings/" + id)
                .contentType("application/json;charset=UTF-8"));

        // then
        String response = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + response);

        resultActions.andExpect(jsonPath("$.status").value("success"));
    }
    



}
