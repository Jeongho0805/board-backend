package com.jeongho.board.security.config;

import com.jeongho.board.repository.UserRepository;
import com.jeongho.board.security.exception.CustomAccessDeniedHandler;
import com.jeongho.board.security.exception.CustomAuthenticationEntryPoint;
import com.jeongho.board.security.exception.JwtTokenExceptionFilter;
import com.jeongho.board.security.filter.*;
import com.jeongho.board.security.provider.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;

    @Autowired
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().configurationSource(corsConfigurationSource()); // crossOrigin 처리
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(new MyCustomDsl())
                .and()
                .addFilterAfter(new JwtTokenExceptionFilter(), LogoutFilter.class)
                .authenticationProvider(customAuthenticationProvider())
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/users").permitAll()
                .antMatchers(HttpMethod.GET, "/boards").permitAll()
                .antMatchers(HttpMethod.GET, "/users").access("hasRole('ADMIN')")
                .antMatchers("/users/**", "/boards/**", "/replys/**").access("hasRole('USER') or hasRole('ADMIN')")
                .anyRequest().permitAll();

        return http.build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
            customAuthenticationFilter.setAuthenticationManager(authenticationManager);
            http
                    .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(customAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        }
    }

    @Bean
    public CustomAuthorizationFilter customAuthorizationFilter() {
        return new CustomAuthorizationFilter(userRepository);
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(passwordEncoder());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
