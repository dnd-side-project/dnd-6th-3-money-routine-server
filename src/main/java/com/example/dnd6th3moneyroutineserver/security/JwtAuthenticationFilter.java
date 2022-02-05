package com.example.dnd6th3moneyroutineserver.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //헤더에서 JWT 반환
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        //토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            //유효한 토큰 -> 유저 정보 반환
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            //SecurityContext에 Authentication 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
