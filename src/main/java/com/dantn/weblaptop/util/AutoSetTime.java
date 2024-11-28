package com.dantn.weblaptop.util;

import com.dantn.weblaptop.config.JwtUtil;
import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.repository.NhanVien_Repositoy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Optional;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */
@Component
public class AutoSetTime {
    private AuthenticationManager authenticationManager;
    @PrePersist
    public void onCreate(BaseEntity entity) {
        entity.setNgayTao(getCurrentTimes());
        entity.setNgaySua(getCurrentTimes());
        entity.setNguoiTao(getNameStaff());
    }

    @PreUpdate
    public void onUpdate(BaseEntity entity) {
        entity.setNgaySua(getCurrentTimes());
        entity.setNguoiSua(getNameStaff());
    }

    public Long getCurrentTimes() {
        return Calendar.getInstance().getTimeInMillis();
    }
    public String getNameStaff() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            return authentication.getName();
//        } else return "Systerm filled data";
        Optional<String> optional = JwtUtil.getCurrentUserLogin();
        if (optional.isPresent()) {
            if(optional.get().equals("anonymousUser")){
                return "Nguyễn Tiến Mạnh";
            }
            return optional.get();
        }
        return "Chưa xác định";
    }
}
