package com.dantn.weblaptop.service;


import com.dantn.weblaptop.dto.request.create_request.CreateDotGiamGiaRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateGotGiamGiaRequest;
import com.dantn.weblaptop.dto.response.DotGiamGiaResponse;
import com.dantn.weblaptop.exception.AppException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public interface DotGiamGiaService {
    Page<DotGiamGiaResponse> findAll(int page, int size);

    DotGiamGiaResponse findById(Long id);

    DotGiamGiaResponse save(CreateDotGiamGiaRequest request) throws AppException;

    DotGiamGiaResponse update(Long id, UpdateGotGiamGiaRequest request) throws AppException;

    Page<DotGiamGiaResponse> filter(String tenOrMa, Integer trangThai, LocalDateTime startDay, LocalDateTime endDay, Integer page, Integer size);

    void changeStatusDotGiamGia(Long id);

    void changeStatusDotGiamGiaStart(Long id);

    void changeStatusDotGiamGiaStop(Long id);
}
