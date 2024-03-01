package com.example.shipgofunding.funding;

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

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(new FundingController(fundingService)).build();
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


}
