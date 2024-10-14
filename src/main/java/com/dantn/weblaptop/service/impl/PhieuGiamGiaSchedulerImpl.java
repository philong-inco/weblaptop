package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.repository.PhieuGiamGiaRepo;
import com.dantn.weblaptop.service.PhieuGiamGiaScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PhieuGiamGiaSchedulerImpl implements PhieuGiamGiaScheduler {
    @Autowired
    private PhieuGiamGiaRepo phieuGiamGiaRepo;

    @Override
    @Scheduled(fixedRate = 2000) // Chạy mỗi 60 giây
    public void updateDiscountsStatus() {
        LocalDateTime now = LocalDateTime.now();
        List<PhieuGiamGia> allPhieuGiamGia = phieuGiamGiaRepo.findAll();

        for (PhieuGiamGia phieuGiamGia : allPhieuGiamGia) {
            if (phieuGiamGia.getTrangThai() == 4) {
                continue; // Bỏ qua phiếu giảm giá với trạng thái 4
            }
            if(phieuGiamGia.getSoLuong() == 0){
                phieuGiamGia.setTrangThai(2);
            }else if (phieuGiamGia.getTrangThai() != 3) { // Chỉ cập nhật nếu phiếu giảm giá không bị hủy
                if (phieuGiamGia.getNgayHetHan().isBefore(now)) {
                    phieuGiamGia.setTrangThai(2); // Cập nhật trạng thái thành "Hết hạn"
                } else if (phieuGiamGia.getNgayBatDau().isAfter(now)) {
                    phieuGiamGia.setTrangThai(0); // Cập nhật trạng thái thành "Chưa áp dụng"
                } else if (phieuGiamGia.getNgayBatDau().isBefore(now) && phieuGiamGia.getNgayHetHan().isAfter(now)) {
                    phieuGiamGia.setTrangThai(1); // Cập nhật trạng thái thành "Đang áp dụng"
                }
            }
            phieuGiamGiaRepo.save(phieuGiamGia);
        }
    }
}
