package com.dantn.weblaptop.mapper;

import com.dantn.weblaptop.dto.request.create_request.CreateDotGiamGiaRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateGotGiamGiaRequest;
import com.dantn.weblaptop.dto.response.DotGiamGiaResponse;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;



@Mapper
public interface DotGiamGiaMapper {
    DotGiamGia createRequestToDotGiamGia(CreateDotGiamGiaRequest request);

    DotGiamGiaResponse dotGiamGiaToDotGiamGiaResponse(DotGiamGia dotGiamGia);

    Page<DotGiamGiaResponse> findAllRequestToDotGiamGiaResponse(Page<DotGiamGia> page);

    DotGiamGia updateDotGiamGia(DotGiamGia dotGiamGia, UpdateGotGiamGiaRequest request);
}
