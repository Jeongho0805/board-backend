package com.jeongho.board.security.filter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeongho.board.domain.User;
import com.jeongho.board.dto.LoginRequestDto;
import com.jeongho.board.security.token.CustomAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.EOFException;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    public CustomAuthenticationFilter() {
        super(new AntPathRequestMatcher("/token/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        log.info("attempAuthentication 진입");

        LoginRequestDto loginRequestDto = objectMapper.readValue(request.getReader(), LoginRequestDto.class);
        if (!StringUtils.hasText(loginRequestDto.getUserId()) || !StringUtils.hasText(loginRequestDto.getPassword())) {
            throw new IllegalArgumentException("username or Password is empty");
        }
        CustomAuthenticationToken customAuthenticationToken = new CustomAuthenticationToken(loginRequestDto.getUserId(), loginRequestDto.getPassword());
        return getAuthenticationManager().authenticate(customAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        log.info("successfulAuthentication 진입");

        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("jeongho0805".getBytes());
        String access_token = JWT.create()
                .withSubject(user.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURI().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(user.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(request.getRequestURI().toString())
                .sign(algorithm);

        response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);

        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = "Invalid Username or Password";

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        if(exception instanceof BadCredentialsException) {
            errorMessage = "비밀번호를 잘못 입력하셨습니다.";
        }
        objectMapper.writeValue(response.getWriter(), errorMessage);
    }
}
