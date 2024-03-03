package com.example.shipgofunding.config.SNS;

import com.example.shipgofunding.user.response.UserResponse;

import java.util.Map;

public class NaverResponseDetails implements UserResponse.OAuth2ResponseDTO {

    private final Map<String,Object> attriibute;

    public NaverResponseDetails(Map<String, Object> attriibute) {
        this.attriibute = (Map<String,Object>) attriibute.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attriibute.get("id").toString();
    }

    @Override
    public String getemail() {
        return attriibute.get("email").toString();
    }

    @Override
    public String getname() {
        return attriibute.get("name").toString();
    }
}
