package dev.mju.jobport.modules.auth.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "kakao-user-info",
        url = "https://kapi.kakao.com/v1/oidc/userinfo"
)
public interface KakaoUserInfoClient {

    @GetMapping
    SocialUserInfo getUserInfo(@RequestHeader("Authorization") String authToken);

}

