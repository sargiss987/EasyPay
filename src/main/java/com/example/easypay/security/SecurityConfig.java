package com.example.easypay.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

  private static final String[] PRIVATE_URLS = {"/api/v1/account/**", "/api/v1/bank/**"};

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().ignoringAntMatchers("/h2/**");
    http.headers().frameOptions().sameOrigin();
    http.authorizeRequests()
        .antMatchers("*")
        .permitAll()
        .antMatchers(PRIVATE_URLS)
        .authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
        .logout()
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/")
        .permitAll();
    return http.build();
  }
}
