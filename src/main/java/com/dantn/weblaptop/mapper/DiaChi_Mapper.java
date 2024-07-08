package com.dantn.weblaptop.mapper;

import com.dantn.weblaptop.dto.request.create_request.CreateDiaChi;
import com.dantn.weblaptop.dto.request.update_request.UpdateDiaChi;
import com.dantn.weblaptop.dto.response.DiaChi_Response;
import com.dantn.weblaptop.entity.khachhang.DiaChi;

import java.util.List;

public interface DiaChi_Mapper {
    DiaChi CreateToEntityDiaChi(CreateDiaChi dto);

    DiaChi UpdateToEntity(UpdateDiaChi dto);

    DiaChi_Response EntiyToResponse(DiaChi entity);

    List<DiaChi_Response> listNhanVienEntityToNhanVienResponse(List<DiaChi> nhanVienList);
}
