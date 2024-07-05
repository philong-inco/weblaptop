package com.dantn.weblaptop.sanpham.thuoctinh.nhucau.service;

import com.dantn.weblaptop.entity.sanpham.NhuCau;
import com.dantn.weblaptop.sanpham.generics.GenericsService;
import com.dantn.weblaptop.sanpham.generics.IGenericsMapper;
import com.dantn.weblaptop.sanpham.generics.IGenericsRepository;
import com.dantn.weblaptop.sanpham.thuoctinh.nhucau.dto.request.NhuCauCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.nhucau.dto.request.NhuCauUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.nhucau.dto.response.NhuCauResponse;
import com.dantn.weblaptop.sanpham.util.GenerateCode;
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
