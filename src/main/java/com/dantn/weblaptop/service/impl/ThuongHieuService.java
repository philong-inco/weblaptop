package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.entity.sanpham.ThuongHieu;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.generics.IGenericsMapper;
import com.dantn.weblaptop.generics.IGenericsRepository;
import com.dantn.weblaptop.dto.request.create_request.ThuongHieuCreate;
import com.dantn.weblaptop.dto.request.update_request.ThuongHieuUpdate;
import com.dantn.weblaptop.dto.response.ThuongHieuResponse;
import com.dantn.weblaptop.util.GenerateCode;
import org.springframework.stereotype.Service;

@Service
public class ThuongHieuService extends GenericsService<ThuongHieu, Long, ThuongHieuCreate, ThuongHieuUpdate, ThuongHieuResponse> {
    public ThuongHieuService(IGenericsRepository<ThuongHieu, Long> genericsRepository, IGenericsMapper<ThuongHieu, ThuongHieuCreate, ThuongHieuUpdate, ThuongHieuResponse> genericsMapper) {
        super(genericsRepository, genericsMapper);
    }

    @Override
    public void convertCreateToEntity(ThuongHieuCreate create, ThuongHieu entity) {
        String ma;
        do {
            ma = GenerateCode.generateCode(GenerateCode.THUONG_HIEU_PREFIX);
        } while (existByCode(ma));
        entity.setMa(ma);
    }

    @Override
    public void convertUpdateToEntity(ThuongHieuUpdate update, ThuongHieu entity) {

    }

    @Override
    public void beforeAdd(ThuongHieuCreate create) {
        if (genericsRepository.isExistName(create.getTen().trim()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }

    @Override
    public void beforeUpdate(ThuongHieuUpdate update) {
        if (genericsRepository.isExistNameAndDifferentId(update.getTen().trim(), update.getId()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }
}
