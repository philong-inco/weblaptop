package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.AnhSanPhamCreate;
import com.dantn.weblaptop.dto.request.update_request.AnhSanPhamUpdate;
import com.dantn.weblaptop.dto.response.AnhSanPhamResponse;
import com.dantn.weblaptop.entity.sanpham.AnhSanPham;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnhSanPhamService {

    AnhSanPhamResponse create(AnhSanPhamCreate create);

    boolean delete(Long idAnhSanPham);
    boolean deleteByIdSPCT(Long idSPCT);

    List<AnhSanPhamResponse> create(List<AnhSanPhamCreate> creates);

    AnhSanPhamResponse update(AnhSanPhamUpdate update);
    List<AnhSanPhamResponse> getAllBySPCTId(Long idSPCT);

    void deleteAllByIdSPCT(Long idSPCT);

}
