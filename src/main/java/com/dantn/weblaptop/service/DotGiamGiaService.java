package com.dantn.weblaptop.service;


import com.dantn.weblaptop.dto.request.create_request.CreateDotGiamGiaRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateGotGiamGiaRequest;
import com.dantn.weblaptop.dto.response.DotGiamGiaResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public interface DotGiamGiaService {
    Page<DotGiamGiaResponse> findAll(int page, int size);

    DotGiamGiaResponse findById(Long id);

    DotGiamGiaResponse save(CreateDotGiamGiaRequest request);

    DotGiamGiaResponse update(Long id, UpdateGotGiamGiaRequest request);

//    void delete(Long id);
//    void saveDotGiamGia(DotGiamGia dotGiamGia);
}
