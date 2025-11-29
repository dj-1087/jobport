package dev.mju.jobport.modules.auth.application;

import dev.mju.jobport.modules.auth.api.client.KakaoTokenRequestClient;
import dev.mju.jobport.modules.auth.api.client.KakaoUserInfoClient;
import dev.mju.jobport.modules.auth.api.client.SocialLoginToken;
import dev.mju.jobport.modules.auth.api.client.SocialUserInfo;
import dev.mju.jobport.modules.auth.config.KakaoOAuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final String GRANT_TYPE = "authorization_code";

    private final KakaoOAuthProperties kakaoOAuthProperties;
    private final KakaoTokenRequestClient kakaoTokenRequestClient;
    private final KakaoUserInfoClient kakaoUserInfoClient;

    public SocialLoginToken requestToken(String code) {
        return kakaoTokenRequestClient.getToken(
                GRANT_TYPE,
                kakaoOAuthProperties.getClientId(),
                kakaoOAuthProperties.getRedirectUri(),
                code,
                kakaoOAuthProperties.getClientSecret()
        );
    }

    public SocialUserInfo requestUserInfo(SocialLoginToken socialLoginToken) {
        String authToken = socialLoginToken.tokenType() + " " + socialLoginToken.accessToken();
        return kakaoUserInfoClient.getUserInfo(authToken);
    }
}
