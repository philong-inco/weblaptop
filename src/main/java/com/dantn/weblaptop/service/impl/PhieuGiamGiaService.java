package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.response.Meta;
import com.dantn.weblaptop.dto.response.PhieuGiamGiaResponse;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.mapper.impl.PhieuGiamGiaMapper;
import com.dantn.weblaptop.repository.PhieuGiamGiaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhieuGiamGiaService {
    @Autowired
    private PhieuGiamGiaRepo phieuGiamGiaRepo;

    public ResultPaginationResponse getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ngayTao").descending());
        Page<PhieuGiamGia> phieuGiamGiaPage = phieuGiamGiaRepo.findAll(pageable);
        // chuyển thành response :
        Page<PhieuGiamGiaResponse> responses = phieuGiamGiaPage.map(
                phieuGiamGia -> PhieuGiamGiaMapper.toPhieuGiamGiaResponse(phieuGiamGia));
        // 2 đối tượng này dùng đề format lại dữ liệu trả về
        // mate : danh cho phân trang
        // ResultPaginationResponse : là kết quả trả về
        Meta meta = Meta.builder()
                .page(responses.getNumber())
                .pageSize(responses.getSize())
                .pages(responses.getTotalPages())
                .total(responses.getTotalElements())
                .build();

        ResultPaginationResponse response = ResultPaginationResponse
                .builder()
                .meta(meta)
                .result(responses.getContent())// trả về list
                .build();

        return response;
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

    public PhieuGiamGiaResponse detail(Long id) {
        Optional<PhieuGiamGia> optional = phieuGiamGiaRepo.findById(id);
        if(optional.isPresent()){
          return   PhieuGiamGiaMapper.toPhieuGiamGiaResponse(optional.get());
        }
        return null;
    }

}
