package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.repository.PhieuGiamGiaRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PhieuGiamGiaService {
    @Autowired
    private PhieuGiamGiaRepo phieuGiamGiaRepo;

    public Page<PhieuGiamGia> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        return phieuGiamGiaRepo.findAll(pageable);
    }

    private String generateUniqueCode() {
        return "PGG" + System.currentTimeMillis();
    }

    public PhieuGiamGia add(PhieuGiamGia phieuGiamGia) {
        phieuGiamGia.setMa(generateUniqueCode());
        return phieuGiamGiaRepo.save(phieuGiamGia);
    }

    public PhieuGiamGia update(PhieuGiamGia phieuGiamGia, Long id) {
        Optional<PhieuGiamGia> optional = phieuGiamGiaRepo.findById(id);
        return optional.map(o -> {
            o.setTen(phieuGiamGia.getTen());
            o.setGiamToiGia(phieuGiamGia.getGiamToiGia());
            o.setGiaTriGiamGia(phieuGiamGia.getGiaTriGiamGia());
            o.setNgayBatDau(phieuGiamGia.getNgayBatDau());
            o.setNgayHetHan(phieuGiamGia.getNgayHetHan());
            o.setLoaiGiamGia(phieuGiamGia.getLoaiGiamGia());
            o.setTrangThai(phieuGiamGia.getTrangThai());
            o.setMoTa(phieuGiamGia.getMoTa());
            o.setGiaTriDonToiThieu(phieuGiamGia.getGiaTriDonToiThieu());

            return phieuGiamGiaRepo.save(o);
        }).orElse(null);
    }


    public PhieuGiamGia delete(Long id) {
        Optional<PhieuGiamGia> optional = phieuGiamGiaRepo.findById(id);
        return optional.map(o -> {
            o.setTrangThai(0); // Giả sử trạng thái "Ngừng hoạt động" = 0
            return phieuGiamGiaRepo.save(o);
        }).orElse(null);
    }

    public PhieuGiamGia detail(Long id){
        Optional<PhieuGiamGia> optional = phieuGiamGiaRepo.findById(id);
        return optional.orElse(null);
    }

}
