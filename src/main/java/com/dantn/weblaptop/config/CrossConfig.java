package com.dantn.weblaptop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;


@Configuration
public class CrossConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));  // Cho phép tất cả các nguồn
        corsConfiguration.addAllowedMethod("*");  // Cho phép tất cả các phương thức (GET, POST, PUT, DELETE, v.v.)
        corsConfiguration.addAllowedHeader("*");  // Cho phép tất cả các headers

        // Tạo UrlBasedCorsConfigurationSource và đăng ký cấu hình CORS
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }


}
