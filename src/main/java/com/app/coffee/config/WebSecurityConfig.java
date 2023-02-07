package com.app.coffee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.app.coffee.utils.CustomSimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico");
    }
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler(){
        return new CustomSimpleUrlAuthenticationSuccessHandler();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                    .cors().disable()
                    .authorizeHttpRequests()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .anyRequest().authenticated()
                    .and()
                    .formLogin()
                        .loginPage("/login")
                                             
                        .usernameParameter("email")
                        .successHandler(customAuthenticationSuccessHandler())
                    .and().logout()
                    .and().build();
    }
}