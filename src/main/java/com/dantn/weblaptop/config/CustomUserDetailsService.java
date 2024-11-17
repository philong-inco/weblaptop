package com.dantn.weblaptop.config;

import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import com.dantn.weblaptop.repository.KhachHangRepository;
import com.dantn.weblaptop.repository.KhachHang_Repository;
import com.dantn.weblaptop.repository.NhanVienVaiTroRepository;
import com.dantn.weblaptop.repository.NhanVien_Repositoy;
import com.dantn.weblaptop.repository.VaiTro_Repository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    NhanVien_Repositoy nhanVienRepositoy;
    KhachHang_Repository khachHangRepository;
    NhanVienVaiTroRepository nhanVienVaiTroRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NhanVien nhanVien = nhanVienRepositoy.findByEmail(username.trim());
        KhachHang khachHang = khachHangRepository.findKhachHangByEmail(username.trim());
        if (nhanVien != null) {
            String[] nhanVienVaiTro = nhanVienVaiTroRepository.findByIdNhanVien(nhanVien.getId())
                    .stream().map(VaiTro::getTen).toArray(String[]::new);
            return User.builder()
                    .username(nhanVien.getEmail())
                    .password(nhanVien.getMatKhau())
                    .roles(nhanVienVaiTro)
                    .build();
        } else if (khachHang != null) {
            return User.builder()
                    .username(khachHang.getEmail())
                    .password(khachHang.getMatKhau())
                    .roles("CUSTOMER")
                    .build();
        } else if (nhanVien == null && khachHang == null){
            throw new UsernameNotFoundException("User not found");
        }
        return null;
    }
}
