package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.entity.sanpham.NhuCau;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.generics.IGenericsMapper;
import com.dantn.weblaptop.generics.IGenericsRepository;
import com.dantn.weblaptop.dto.request.create_request.NhuCauCreate;
import com.dantn.weblaptop.dto.request.update_request.NhuCauUpdate;
import com.dantn.weblaptop.dto.response.NhuCauResponse;
import com.dantn.weblaptop.util.GenerateCode;
import org.springframework.stereotype.Service;

@Service
public class NhuCauService extends GenericsService<NhuCau, Long, NhuCauCreate, NhuCauUpdate, NhuCauResponse> {
    public NhuCauService(IGenericsRepository<NhuCau, Long> genericsRepository, IGenericsMapper<NhuCau, NhuCauCreate, NhuCauUpdate, NhuCauResponse> genericsMapper) {
        super(genericsRepository, genericsMapper);
    }

    @Override
    public void convertCreateToEntity(NhuCauCreate create, NhuCau entity) {
        String ma;
        do {
            ma = GenerateCode.generateCode(GenerateCode.NHU_CAU_PREFIX);
        } while (existByCode(ma));
        entity.setMa(ma);
    }

    @Override
    public void convertUpdateToEntity(NhuCauUpdate update, NhuCau entity) {

    }

    @Override
    public void beforeAdd(NhuCauCreate create) {
        if (genericsRepository.isExistName(create.getTen().trim()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }

    @Override
    public void beforeUpdate(NhuCauUpdate update) {
        if (genericsRepository.isExistNameAndDifferentId(update.getTen().trim(), update.getId()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }
}
