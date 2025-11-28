package dev.mju.jobport.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // csrf는 REST + SPA 구조면 끄기도 하는데, 일반 thymeleaf라면 기본 on 유지 추천
//                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/login", "/oauth/**",
                                "/css/**", "/js/**", "/img/**", "/vendor/**", "/favicon.ico",
                                "/recruitments/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // formLogin은 사용 안 하고, 카카오 로그인만 쓴다고 가정
                .formLogin(form -> form.disable())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}
