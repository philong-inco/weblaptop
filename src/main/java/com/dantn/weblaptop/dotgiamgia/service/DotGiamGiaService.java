package com.dantn.weblaptop.dotgiamgia.service;


import com.dantn.weblaptop.dotgiamgia.model.request.create_request.CreateDotGiamGiaRequest;
import com.dantn.weblaptop.dotgiamgia.model.request.update_request.UpdateGotGiamGiaRequest;
import com.dantn.weblaptop.dotgiamgia.model.response.DotGiamGiaResponse;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface DotGiamGiaService {
    Page<DotGiamGiaResponse> findAll(int page, int size);

    DotGiamGiaResponse findById(Long id);

    DotGiamGiaResponse save(CreateDotGiamGiaRequest request);

    DotGiamGiaResponse update(Long id, UpdateGotGiamGiaRequest request);

//    void delete(Long id);
//    void saveDotGiamGia(DotGiamGia dotGiamGia);
}
