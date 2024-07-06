package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.HeDieuHanh;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.generics.IGenericsMapper;
import com.dantn.weblaptop.generics.IGenericsRepository;
import com.dantn.weblaptop.dto.request.create_request.HeDieuHanhCreate;
import com.dantn.weblaptop.dto.request.update_request.HeDieuHanhUpdate;
import com.dantn.weblaptop.dto.response.HeDieuHanhResponse;
import com.dantn.weblaptop.util.GenerateCode;
import org.springframework.stereotype.Service;

@Service
public class HeDieuHanhService extends GenericsService<HeDieuHanh, Long, HeDieuHanhCreate, HeDieuHanhUpdate, HeDieuHanhResponse> {

    public HeDieuHanhService(IGenericsRepository<HeDieuHanh, Long> genericsRepository, IGenericsMapper<HeDieuHanh, HeDieuHanhCreate, HeDieuHanhUpdate, HeDieuHanhResponse> genericsMapper) {
        super(genericsRepository, genericsMapper);
    }

    @Override
    public void convertCreateToEntity(HeDieuHanhCreate create, HeDieuHanh entity) {
        String ma;
        do {
            ma = GenerateCode.generateCode(GenerateCode.HE_DIEU_HANH_PREFIX);
        } while (existByCode(ma));
        entity.setMa(ma);
    }

    @Override
    public void convertUpdateToEntity(HeDieuHanhUpdate update, HeDieuHanh entity) {

    }

    @Override
    public void beforeAdd(HeDieuHanhCreate create) {
        if (genericsRepository.isExistName(create.getTen().trim()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }

    @Override
    public void beforeUpdate(HeDieuHanhUpdate update) {
        if (genericsRepository.isExistNameAndDifferentId(update.getTen().trim(), update.getId()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }
}
