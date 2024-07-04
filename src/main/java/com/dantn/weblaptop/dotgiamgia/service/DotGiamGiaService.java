package com.dantn.weblaptop.dotgiamgia.service;

import com.dantn.weblaptop.dotgiamgia.model.request.CreateDotGiamGiaRequest;
import com.dantn.weblaptop.dotgiamgia.model.request.UpdateDotGiamGiaRequest;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DotGiamGiaService {
    List<DotGiamGia> findAll();

    DotGiamGia findById(Long id);

    void save(CreateDotGiamGiaRequest request);

    void update(Long id, UpdateDotGiamGiaRequest request);

    void delete(Long id);
}
