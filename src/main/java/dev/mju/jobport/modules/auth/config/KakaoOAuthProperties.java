package dev.mju.jobport.modules.auth.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "auth.kakao")
public class KakaoOAuthProperties {
    private final String clientId;
    private final String responseType;
    private final String authorizeUri;
    private final String redirectUri;
    private final String clientSecret;
}
