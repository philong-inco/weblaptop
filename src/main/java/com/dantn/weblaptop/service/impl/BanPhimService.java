package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.BanPhim;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.generics.IGenericsMapper;
import com.dantn.weblaptop.generics.IGenericsRepository;
import com.dantn.weblaptop.dto.request.create_request.BanPhimCreate;
import com.dantn.weblaptop.dto.request.update_request.BanPhimUpdate;
import com.dantn.weblaptop.dto.response.BanPhimResponse;
import com.dantn.weblaptop.util.GenerateCode;
import org.springframework.stereotype.Service;

@Service
public class BanPhimService extends GenericsService<BanPhim, Long, BanPhimCreate, BanPhimUpdate, BanPhimResponse> {
    public BanPhimService(IGenericsRepository<BanPhim, Long> genericsRepository, IGenericsMapper<BanPhim, BanPhimCreate, BanPhimUpdate, BanPhimResponse> genericsMapper) {
        super(genericsRepository, genericsMapper);
    }

    @Override
    public void convertCreateToEntity(BanPhimCreate create, BanPhim entity) {
        String ma;
        do {
            ma = GenerateCode.generateCode(GenerateCode.BAN_PHIM_PREFIX);
        } while (existByCode(ma));
        entity.setMa(ma);
    }

    @Override
    public void convertUpdateToEntity(BanPhimUpdate update, BanPhim entity) {

    }

    @Override
    public void beforeAdd(BanPhimCreate create) {
        if (genericsRepository.isExistName(create.getTen().trim()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }

    @Override
    public void beforeUpdate(BanPhimUpdate update) {
        if (genericsRepository.isExistNameAndDifferentId(update.getTen().trim(), update.getId()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }
}
