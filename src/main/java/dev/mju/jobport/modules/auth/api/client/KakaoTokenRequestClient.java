package dev.mju.jobport.modules.auth.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "kakao-token-request",
        url = "https://kauth.kakao.com/oauth/token"
)
public interface KakaoTokenRequestClient {

    @PostMapping(consumes = "application/x-www-form-urlencoded;charset=utf-8")
    SocialLoginToken getToken(
            @RequestParam(value = "grant_type") String grantType,
            @RequestParam(value = "client_id") String clientId,
            @RequestParam(value = "redirect_uri") String redirectUri,
            @RequestParam(value = "code") String code,
            @RequestParam(value = "client_secret") String clientSecret
    );

}

