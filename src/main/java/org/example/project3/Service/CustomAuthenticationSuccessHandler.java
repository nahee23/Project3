package org.example.project3.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName(); // 로그인한 사용자의 이메일

        String redirectUrl;

        // 이메일이 "knh3136@naver.com"일 경우 /admin으로 리다이렉트
        if ("knh3136@naver.com".equals(username)) {
            redirectUrl = "/admin";  // 관리 페이지
        } else {
            redirectUrl = "/goods";  // 기본 페이지
        }

        response.sendRedirect(redirectUrl); // 리다이렉션 처리
    }
}
