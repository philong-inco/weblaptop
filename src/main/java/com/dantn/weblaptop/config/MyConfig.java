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
                 //Phần của Long (ae không sửa)
                .requestMatchers(
                        "/api/ban-phim/add",
                        "/api/ban-phim/update/**",
                        "/api/he-dieu-hanh/add",
                        "/api/he-dieu-hanh/update/**",
                        "/api/man-hinh/add",
                        "/api/man-hinh/update/**",
                        "/api/mau-sac/add",
                        "/api/mau-sac/update/**",
                        "/api/nhu-cau/add",
                        "/api/nhu-cau/update/**",
                        "/api/o-cung/add",
                        "/api/o-cung/update/**",
                        "/api/ram/add",
                        "/api/ram/update/**",
                        "/api/san-pham-chi-tiet/add",
                        "/api/san-pham-chi-tiet/change-status**",
                        "/api/san-pham-chi-tiet/update-price-image",
                        "/api/san-pham-chi-tiet/update/**",
                        "/api/san-pham/add",
                        "/api/san-pham/update/**",
                        "/api/san-pham/change-status",
                        "/api/san-pham/updateSanPhamAndSPCT/**",
                        "/api/serial-number/add",
                        "/api/serial-number/update/**",
                        "/api/serial-number/change-status-to-serial-da-ban/**",
                        "/api/thuong-hieu/add",
                        "/api/thuong-hieu/update/**",
                        "/api/vga/add",
                        "/api/vga/update/**",
                        "/api/webcam/add",
                        "/api/webcam/update/**"
                ).hasAnyRole("ADMIN")
//                .requestMatchers(
//                        "api/sell/**",
//                        "/api/bills/code/**")
//                        .hasAnyRole("ADMIN", "STAFF")
//                .requestMatchers("api/sell/**",
//                        "/api/bills/create/**")
//                                .hasAnyRole("ADMIN")
                .requestMatchers(
                        "/api/ban-phim/delete/**",
                        "/api/he-dieu-hanh/delete/**",
                        "/api/man-hinh/delete/**",
                        "/api/mau-sac/delete/**",
                        "/api/nhu-cau/delete/**",
                        "/api/o-cung/delete/**",
                        "/api/ram/delete/**",
                        "/api/san-pham-chi-tiet/delete/**",
                        "/api/san-pham/delete/**",
                        "/api/serial-number/delete/**",
                        "/api/thuong-hieu/delete/**",
                        "/api/vga/delete/**",
                        "/api/webcam/delete/**"
                ).hasAnyRole( "ADMIN")

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
