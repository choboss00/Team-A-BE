package com.example.shipgofunding.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public interface OAuth2ResponseDTO{
    @Schema(description = "제공사이트", example = "naver")
    String getProvider();

    @Schema(description = "사이트 발급 id")
    String getProviderId();

    @Schema(description = "사용자 이메일", example = "test@naver.com")
    String getemail();

    @Schema(description = "사용자 실명", example = "김채흔")
    String getname();

}