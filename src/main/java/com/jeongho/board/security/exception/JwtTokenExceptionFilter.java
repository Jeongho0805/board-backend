package com.jeongho.board.security.exception;

import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.MalformedInputException;

public class JwtTokenExceptionFilter extends OncePerRequestFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (InvalidClaimException e) {
            sendErrorResponse(response, "유효하지 않은 사용자입니다.");
        } catch (TokenExpiredException e) {
            sendErrorResponse(response, "유효기간이 만료된 토큰입니다.");
        } catch (JWTDecodeException e) {
            sendErrorResponse(response, "유효하지 않은 토큰입니다.");
        }
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), message);
    }
}
