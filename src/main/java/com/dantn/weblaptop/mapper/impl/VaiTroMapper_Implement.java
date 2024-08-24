package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateVaiTro_Request;
import com.dantn.weblaptop.dto.request.update_request.UpdateVaiTro_Request;
import com.dantn.weblaptop.dto.response.VaiTro_Response;
import com.dantn.weblaptop.mapper.VaiTro_Mapper;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class VaiTroMapper_Implement implements VaiTro_Mapper {

    @Override
    public VaiTro CreateToEntity(CreateVaiTro_Request dto) {
        if (dto == null) {
            return null;
        }
        VaiTro vaiTro = new VaiTro();
        vaiTro.setTen(dto.getTen());
        vaiTro.setMoTa(dto.getMoTa());
        vaiTro.setTrangThai(dto.getTrangThai());
        return vaiTro;
    }

    @Override
    public VaiTro UpdateToEntity(UpdateVaiTro_Request dto) {
        if (dto == null) {
            return null;
        }
        VaiTro vaiTro = new VaiTro();
        vaiTro.setTen(dto.getTen());
        vaiTro.setMoTa(dto.getMoTa());
        vaiTro.setTrangThai(dto.getTrangThai());
        return vaiTro;
    }

    @Override
    public VaiTro_Response toResponse(VaiTro entity) {
        if (entity == null) {
            return null;
        }
        VaiTro_Response response = new VaiTro_Response();
        response.setId(entity.getId());
        response.setTen(entity.getTen());
        response.setMoTa(entity.getMoTa());
        response.setMa(entity.getMa());
        response.setTrang_thai(entity.getTrangThai());
        return response;
    }

    @Override
    public List<VaiTro_Response> listVaiTroEntityToVaiTroResponse(List<VaiTro> entityList) {
        List<VaiTro_Response> responseList = new ArrayList<>(entityList.size());
        for (VaiTro entity : entityList) {
            responseList.add(toResponse(entity));
        }
        return responseList;
    }
}
