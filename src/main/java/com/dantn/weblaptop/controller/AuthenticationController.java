package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.config.JwtUtil;
import com.dantn.weblaptop.config.dto.AuthRequest;
import com.dantn.weblaptop.config.dto.AuthResponse;
import com.dantn.weblaptop.dto.response.KhachHangResponse;
import com.dantn.weblaptop.dto.response.NhanVienResponse;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import com.dantn.weblaptop.repository.KhachHangRepository;
import com.dantn.weblaptop.repository.NhanVienVaiTroRepository;
import com.dantn.weblaptop.repository.NhanVien_Repositoy;
import com.dantn.weblaptop.service.KhachHang_Service;
import com.dantn.weblaptop.service.NhanVien_Service;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationController {
    AuthenticationManager authenticationManager;
    NhanVien_Service nhanVienService;
    KhachHang_Service khachHangService;
    NhanVienVaiTroRepository vaiTroRepository;
    JwtUtil jwtUtil;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        String token = jwtUtil.generateToken(authRequest.getEmail());
        AuthResponse response = getInfoGuest(authRequest.getEmail());
        response.setJwt(token);

        return ResponseEntity.ok(response);
    }

    public AuthResponse getInfoGuest(String email){
        AuthResponse response = new AuthResponse();
        NhanVienResponse staff = nhanVienService.findByEmail(email);
        KhachHangResponse customer = khachHangService.findKhachHangByEmail(email);
        if (staff == null && customer == null) return null;
        if (staff == null){
            response.setId(customer.getId());
            response.setRole("CUSTOMER");
            response.setTen(customer.getTen());
            response.setEmail(customer.getEmail());
            response.setSdt(customer.getSdt());
            response.setAvatar(customer.getHinhAnh());
        } else {
            List<String> granded = vaiTroRepository.findByIdNhanVien(staff.getId())
                    .stream().map(VaiTro::getTen).collect(Collectors.toList());
            if (granded.size() > 1 && granded.contains("ADMIN")){
                response.setRole("ADMIN");
            } else if (granded.size() == 1 && granded.contains("STAFF")) {
                response.setRole("STAFF");
            }
            response.setId(staff.getId());
            response.setTen(staff.getTen());
            response.setEmail(staff.getEmail());
            response.setSdt(staff.getSdt());
            response.setAvatar(staff.getHinhAnh());
        }

        return response;
    }
}
