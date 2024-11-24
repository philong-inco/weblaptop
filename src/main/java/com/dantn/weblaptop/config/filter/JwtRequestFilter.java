package com.dantn.weblaptop.config.filter;

import com.dantn.weblaptop.config.CustomUserDetailsService;
import com.dantn.weblaptop.config.JwtUtil;
import com.dantn.weblaptop.exception.MyJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    JwtUtil jwtUtil;
    CustomUserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            final String authorizationHeader = request.getHeader("Authorization");
            String email = null;
            String jwt = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                email = jwtUtil.extractUsername(jwt);
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
//        } catch (ExpiredJwtException e) {
////            throw new MyJwtException("Token đã hết hạn", 701);
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write(e.getMessage());
//        } catch (SignatureException e) {
//            throw new MyJwtException("Chữ ký không hợp lệ", 702);
//        } catch (MalformedJwtException e) {
//            throw new MyJwtException("Token không hợp lệ", 703);
//        } catch (Exception e) {
////            throw new MyJwtException("Token không hợp lệ", 704);
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write(e.getMessage());
//        }
        } catch (ExpiredJwtException e) {
            handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "Token đã hết hạn", 701);
        } catch (SignatureException e) {
            handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "Chữ ký không hợp lệ", 702);
        } catch (MalformedJwtException e) {
            handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "Token không hợp lệ", 703);
        } catch (Exception e) {
            handleException(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi không xác định", 704);
        }
    }

    private void handleException(HttpServletResponse response, int status, String message, int errorCode) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(
                String.format("{\"message\": \"%s\", \"code\": %d}", message, errorCode)
        );
        response.getWriter().flush();
    }
}
