package dev.mju.jobport.modules.auth.web;

import dev.mju.jobport.config.security.MemberDetails;
import dev.mju.jobport.modules.auth.api.client.SocialLoginToken;
import dev.mju.jobport.modules.auth.api.client.SocialUserInfo;
import dev.mju.jobport.modules.auth.application.AuthService;
import dev.mju.jobport.modules.auth.config.KakaoOAuthProperties;
import dev.mju.jobport.modules.members.application.MemberService;
import dev.mju.jobport.modules.members.domain.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final KakaoOAuthProperties kakaoOAuthProperties;
    private final AuthService authService;
    private final MemberService memberService;

    @GetMapping("/login")
    public String login(Model model) {
        String url = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com/oauth/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", kakaoOAuthProperties.getClientId())
                .queryParam("redirect_uri", kakaoOAuthProperties.getRedirectUri())
                .build(true)
                .toString();

        return "redirect:" + url;
    }

    @GetMapping("/oauth/kakao/token")
    public String login(@RequestParam String code, HttpServletRequest request) {
        SocialLoginToken socialLoginToken = authService.requestToken(code);
        SocialUserInfo socialUserInfo = authService.requestUserInfo(socialLoginToken);

        Member member = memberService.find(socialUserInfo.sub());

        if (member == null) {
            member = memberService.create(
                    socialUserInfo.sub(),
                    socialUserInfo.nickname(),
                    socialUserInfo.picture()
            );
        }

        MemberDetails memberDetails = new MemberDetails(member);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                memberDetails,
                null,
                memberDetails.getAuthorities()
        );

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);


        return "redirect:/";
    }

}
