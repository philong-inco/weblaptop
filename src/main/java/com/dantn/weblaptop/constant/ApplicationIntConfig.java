package com.dantn.weblaptop.constant;

import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.repository.KhachHangRepository;
import com.dantn.weblaptop.repository.NhanVien_Repositoy;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationIntConfig {

    @Bean
    ApplicationRunner applicationRunner(
            NhanVien_Repositoy userReposiory,
            KhachHangRepository customerRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
//            List<NhanVien> nhanVienList = userReposiory.findAll();
//            for (NhanVien nhanVien : nhanVienList) {
//                nhanVien.setMatKhau(passwordEncoder.encode("manh19924"));
//                userReposiory.save(nhanVien);
//            }
//
//            List<KhachHang> khachHangList = customerRepository.findAll();
//            for (KhachHang khachHang : khachHangList) {
//                khachHang.setMatKhau(passwordEncoder.encode("manh19925"));
//                customerRepository.save(khachHang);
//            }
        };
    }
}
