package com.example.shipgofunding.user.service;

import com.example.shipgofunding.config.SNS.CUstomOAuth2User;
import com.example.shipgofunding.config.SNS.KakaoResponseDetails;
import com.example.shipgofunding.config.SNS.NaverResponseDetails;
import com.example.shipgofunding.user.domain.RoleEnum;
import com.example.shipgofunding.user.domain.SnsUser;
import com.example.shipgofunding.user.repository.SnsUserRepository;
import com.example.shipgofunding.user.request.UserRequest;
import com.example.shipgofunding.user.response.UserResponse;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
@PropertySource("classpath:naver.yml")
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final SnsUserRepository snsUserRepository;

    public CustomOAuth2UserService(SnsUserRepository snsUserRepository){
        this.snsUserRepository=snsUserRepository;
    }

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //유저 정보(attributes) 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);

        //resistrationId 가져오기
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        UserResponse.OAuth2ResponseDTO oAuth2ResponseDTO = null;
        if (registrationId.equals("naver")) {
            oAuth2ResponseDTO = new NaverResponseDetails(oAuth2User.getAttributes());
        } else if (registrationId.equals("kakao")) {
            oAuth2ResponseDTO = new KakaoResponseDetails((oAuth2User.getAttributes()));
        } else {
            return null;
        }

        String username = oAuth2ResponseDTO.getProvider() + " " + oAuth2ResponseDTO.getProviderId();
        SnsUser existData = snsUserRepository.findByUsername(username);
        if (existData == null) {
            SnsUser snsUser = new SnsUser();
            snsUser.setUsername(username);
            snsUser.setEmail(oAuth2ResponseDTO.getemail());
            snsUser.setName(oAuth2ResponseDTO.getname());
            snsUser.setRole(RoleEnum.USER);

            snsUserRepository.save(snsUser);

            UserRequest.snsloginDTO snsloginDTO = new UserRequest.snsloginDTO();
            snsloginDTO.setUsername(username);
            snsloginDTO.setName(oAuth2ResponseDTO.getname());
            snsloginDTO.setRole(RoleEnum.USER);

            return new CUstomOAuth2User(snsloginDTO);
        } else {

            existData.setEmail(oAuth2ResponseDTO.getemail());
            existData.setName(oAuth2ResponseDTO.getname());

            snsUserRepository.save(existData);

            UserRequest.snsloginDTO snsloginDTO = new UserRequest.snsloginDTO();
            snsloginDTO.setUsername(existData.getUsername());
            snsloginDTO.setName(oAuth2ResponseDTO.getname());
            snsloginDTO.setRole(existData.getRole());

            return new CUstomOAuth2User(snsloginDTO);
        }
    }
}



