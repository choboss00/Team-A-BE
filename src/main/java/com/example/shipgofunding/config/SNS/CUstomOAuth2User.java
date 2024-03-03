package com.example.shipgofunding.config.SNS;

import com.example.shipgofunding.user.request.UserRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CUstomOAuth2User implements OAuth2User {

    public final UserRequest.snsloginDTO snsloginDTO;

    public CUstomOAuth2User(UserRequest.snsloginDTO snsloginDTO) {
        this.snsloginDTO = snsloginDTO;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection =new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return snsloginDTO.getRole().toString();
            }
        });
        return collection;
    }

    @Override
    public String getName() {
        return snsloginDTO.getName();
    }

    public String getUsername(){
        return snsloginDTO.getUsername();
    }
}
