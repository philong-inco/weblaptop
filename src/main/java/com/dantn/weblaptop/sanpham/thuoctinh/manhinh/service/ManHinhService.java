package com.dantn.weblaptop.sanpham.thuoctinh.manhinh.service;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.ManHinh;
import com.dantn.weblaptop.sanpham.generics.GenericsService;
import com.dantn.weblaptop.sanpham.generics.IGenericsMapper;
import com.dantn.weblaptop.sanpham.generics.IGenericsRepository;
import com.dantn.weblaptop.sanpham.thuoctinh.manhinh.dto.request.ManHinhCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.manhinh.dto.request.ManHinhUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.manhinh.dto.response.ManHinhResponse;
import com.dantn.weblaptop.sanpham.util.GenerateCode;
import org.springframework.stereotype.Service;

@Service
public class ManHinhService extends GenericsService<ManHinh, Long, ManHinhCreate, ManHinhUpdate, ManHinhResponse> {

    public ManHinhService(IGenericsRepository<ManHinh, Long> genericsRepository, IGenericsMapper<ManHinh, ManHinhCreate, ManHinhUpdate, ManHinhResponse> genericsMapper) {
        super(genericsRepository, genericsMapper);
    }

    @Override
    public void convertCreateToEntity(ManHinhCreate create, ManHinh entity) {
        String ma;
        do {
            ma = GenerateCode.generateCode(GenerateCode.MAN_HINH_PREFIX);
        } while (existByCode(ma));
        entity.setMa(ma);
    }

    @Override
    public void convertUpdateToEntity(ManHinhUpdate update, ManHinh entity) {

    }

    @Override
    public void beforeAdd(ManHinhCreate create) {
        if (genericsRepository.isExistName(create.getTen().trim()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }

    @Override
    public void beforeUpdate(ManHinhUpdate update) {
        if (genericsRepository.isExistNameAndDifferentId(update.getTen().trim(), update.getId()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }
}
