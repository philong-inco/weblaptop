package com.dantn.weblaptop.dotgiamgia.service;

import com.dantn.weblaptop.dotgiamgia.dto.DotGiamGiaSanPhamChiTietDTO;
import com.dantn.weblaptop.dotgiamgia.model.request.CreateDotGiamGiaSanPhamChiTietRequest;
import com.dantn.weblaptop.dotgiamgia.model.request.UpdateDotGiamGiaSanPhamChiTietRequest;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGiaSanPhamChiTiet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DotGiamGiaSanPhamChiTietService {
    List<DotGiamGiaSanPhamChiTietDTO> findAll();

    DotGiamGiaSanPhamChiTietDTO findById(Long id);

    void save(CreateDotGiamGiaSanPhamChiTietRequest request);

    void update(Long id, UpdateDotGiamGiaSanPhamChiTietRequest request);

    void delete(Long id);
}
