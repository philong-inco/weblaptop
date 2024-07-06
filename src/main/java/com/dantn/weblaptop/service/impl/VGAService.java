package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.VGA;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.generics.IGenericsMapper;
import com.dantn.weblaptop.generics.IGenericsRepository;
import com.dantn.weblaptop.dto.request.create_request.VGACreate;
import com.dantn.weblaptop.dto.request.update_request.VGAUpdate;
import com.dantn.weblaptop.dto.response.VGAResponse;
import com.dantn.weblaptop.util.GenerateCode;
import org.springframework.stereotype.Service;

@Service
public class VGAService extends GenericsService<VGA, Long, VGACreate, VGAUpdate, VGAResponse> {

    public VGAService(IGenericsRepository<VGA, Long> genericsRepository, IGenericsMapper<VGA, VGACreate, VGAUpdate, VGAResponse> genericsMapper) {
        super(genericsRepository, genericsMapper);
    }

    @Override
    public void convertCreateToEntity(VGACreate create, VGA entity) {
        String ma;
        do {
            ma = GenerateCode.generateCode(GenerateCode.VGA_PREFIX);
        } while (existByCode(ma));
        entity.setMa(ma);
    }

    @Override
    public void convertUpdateToEntity(VGAUpdate update, VGA entity) {
    }

    @Override
    public void beforeAdd(VGACreate create) {
        if (genericsRepository.isExistName(create.getTen().trim()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }

    @Override
    public void beforeUpdate(VGAUpdate update) {
        if (genericsRepository.isExistNameAndDifferentId(update.getTen().trim(), update.getId()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }
}
