package com.dantn.weblaptop.dotgiamgia.model.mapper;

import com.dantn.weblaptop.dotgiamgia.model.request.CreateDotGiamGiaRequest;
import com.dantn.weblaptop.dotgiamgia.model.request.UpdateDotGiamGiaRequest;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;

public interface DotGiamGiaMapper {
    DotGiamGia createDotGiamGia(CreateDotGiamGiaRequest request);

    DotGiamGia updateDotGiamGia(DotGiamGia dotGiamGia, UpdateDotGiamGiaRequest request);
}
