package com.dantn.weblaptop.config;

import com.dantn.weblaptop.config.filter.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MyConfig {
    private CustomUserDetailsService customUserDetailsService;
//    private JwtUtil jwtUtil;
//
//    public MyConfig(CustomUserDetailsService customUserDetailsService, JwtUtil jwtUtil, NhanVien_Repositoy nvRepo, KhachHang_Repository khRepo, NhanVienVaiTroRepository nvvtRepo) {
//        this.customUserDetailsService = new CustomUserDetailsService(nvRepo, khRepo, nvvtRepo);
//        this.jwtUtil = new JwtUtil();
//    }
    private final JwtRequestFilter jwtRequestFilter;

    public MyConfig(JwtRequestFilter jwtRequestFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) -> authz
                .requestMatchers("/api/v1/auth/login").permitAll()
                .requestMatchers("/api/ban-phim/add").hasAnyRole("STAFF", "ADMIN")
                .requestMatchers("/api/ban-phim/update/**").hasAnyRole("STAFF", "ADMIN")
                .requestMatchers("/api/ban-phim/detail/**").hasAnyRole("STAFF", "ADMIN")
                .requestMatchers("/api/ban-phim/exist-code/**").hasAnyRole("STAFF", "ADMIN")
                .requestMatchers("/api/ban-phim/exist-name/**").hasAnyRole("STAFF", "ADMIN")
                .requestMatchers("/api/ban-phim/delete/**").hasAnyRole( "ADMIN")
                //ban-phim
//                .requestMatchers("/api/ban-phim/.*/add").hasAnyRole("STAFF", "ADMIN")
//                .requestMatchers("/api/ban-phim/.*/update/**").hasAnyRole("STAFF", "ADMIN")
//                .requestMatchers("/api/ban-phim/.*/detail/**").hasAnyRole("STAFF", "ADMIN")
//                .requestMatchers("/api/ban-phim/.*/exist-code/**").hasAnyRole("STAFF", "ADMIN")
//                .requestMatchers("/api/ban-phim/.*/exist-name/**").hasAnyRole("STAFF", "ADMIN")
//                .requestMatchers("/api/ban-phim/.*/delete/**").hasAnyRole( "ADMIN")

                // man-hinh
//                .requestMatchers("/api/man-hinh/.*/add").hasAnyRole("STAFF", "ADMIN")
//                .requestMatchers("/api/man-hinh/.*/update/**").hasAnyRole("STAFF", "ADMIN")
//                .requestMatchers("/api/man-hinh/.*/detail/**").hasAnyRole("STAFF", "ADMIN")
//                .requestMatchers("/api/man-hinh/.*/exist-code/**").hasAnyRole("STAFF", "ADMIN")
//                .requestMatchers("/api/man-hinh/.*/exist-name/**").hasAnyRole("STAFF", "ADMIN")
//                .requestMatchers("/api/man-hinh/.*/delete/**").hasAnyRole( "ADMIN")
                .anyRequest().permitAll()
        )
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .csrf(csrf -> csrf.disable());
        return http.build();
    }
    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
