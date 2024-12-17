package org.example.project3.Config;

import lombok.RequiredArgsConstructor;
import org.example.project3.Service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.WebAuthnConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz) ->
                        authz
                                // 관리자 전용 페이지 설정
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                // 회원 전용 페이지 설정
                                .requestMatchers("/user/**").hasRole("USER")
                                // 공용 페이지 설정
                                .requestMatchers("/","/login","/register", "/goods").permitAll()
                                .anyRequest().authenticated()
                ).formLogin((formLogin) ->
                        formLogin
                                .loginPage("/login")
                                .failureUrl("/login")
                                .defaultSuccessUrl("/goods")
                                .usernameParameter("email")
                                .passwordParameter("password")
                ).logout((logout) ->
                        logout
                                .logoutUrl("/logout")
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .logoutSuccessUrl("/login?logout=true")
                                .permitAll()
                );


        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) ->
                web.ignoring().requestMatchers("/css/**", "/js/**", "/error");

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
