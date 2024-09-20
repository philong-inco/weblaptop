package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.repository.DotGiamGiaRepository;
import com.dantn.weblaptop.service.DotGiamGiaScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DotGiamGiaSchedulerImpl implements DotGiamGiaScheduler {
    @Autowired
    private DotGiamGiaRepository dotGiamGiaRepository;

    @Override
    @Scheduled(fixedRate = 2000) // Chạy mỗi 60 giây
    public void updateDggStatus() {
        LocalDateTime now = LocalDateTime.now();
        List<DotGiamGia> allDotGiamGia = dotGiamGiaRepository.findAll();

        for (DotGiamGia dotGiamGia : allDotGiamGia) {
            if (dotGiamGia.getTrangThai() != 3) { // Chỉ cập nhật nếu phiếu giảm giá không bị hủy
                if (dotGiamGia.getThoiGianKetthuc().isBefore(now)) {
                    dotGiamGia.setTrangThai(2); // Cập nhật trạng thái thành "Hết hạn"
                } else if (dotGiamGia.getThoiGianBatDau().isAfter(now)) {
                    dotGiamGia.setTrangThai(0); // Cập nhật trạng thái thành "Chưa áp dụng"
                } else if (dotGiamGia.getThoiGianBatDau().isBefore(now) && dotGiamGia.getThoiGianKetthuc().isAfter(now)) {
                    dotGiamGia.setTrangThai(1); // Cập nhật trạng thái thành "Đang áp dụng"
                }
            }
            dotGiamGiaRepository.save(dotGiamGia);
        }
    }
}
