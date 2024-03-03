package com.example.shipgofunding.config.SNS;

import com.example.shipgofunding.user.response.UserResponse;

import java.util.Map;

public class KakaoResponseDetails implements UserResponse.OAuth2ResponseDTO {

    private final Map<String,Object> kakaoAccount;
    private final Map<String,Object> kakaoProfile;
    private final Map<String,Object> attriibute;

    public KakaoResponseDetails(Map<String, Object> attriibute) {
        this.kakaoAccount = (Map<String,Object>) attriibute.get("kakao_acount");
        this.kakaoProfile = (Map<String,Object>) attriibute.get("profile");
        this.attriibute = (Map<String,Object>) attriibute.get("id");
    }
    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attriibute.get("id").toString();
    }

    @Override
    public String getemail() {
        return kakaoAccount.get("email").toString();
    }

    @Override
    public String getname() {
        return kakaoProfile.get("nickname").toString();
    }
}
