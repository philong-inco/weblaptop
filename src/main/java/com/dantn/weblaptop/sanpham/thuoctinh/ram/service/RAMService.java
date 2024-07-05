package com.dantn.weblaptop.sanpham.thuoctinh.ram.service;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.RAM;
import com.dantn.weblaptop.sanpham.generics.GenericsService;
import com.dantn.weblaptop.sanpham.generics.IGenericsMapper;
import com.dantn.weblaptop.sanpham.generics.IGenericsRepository;
import com.dantn.weblaptop.sanpham.thuoctinh.ram.dto.request.RAMCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.ram.dto.request.RAMUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.ram.dto.response.RAMResponse;
import com.dantn.weblaptop.sanpham.util.GenerateCode;
import org.springframework.stereotype.Service;

@Service
public class RAMService extends GenericsService<RAM, Long, RAMCreate, RAMUpdate, RAMResponse> {

    public RAMService(IGenericsRepository<RAM, Long> genericsRepository,
                      IGenericsMapper<RAM, RAMCreate, RAMUpdate, RAMResponse> genericsMapper) {
        super(genericsRepository, genericsMapper);
    }

    @Override
    public void convertCreateToEntity(RAMCreate create, RAM entity) {
        String ma;
        do {
            ma = GenerateCode.generateCode(GenerateCode.RAM_PREFIX);
        } while (existByCode(ma));
        entity.setMa(ma);
    }

    @Override
    public void convertUpdateToEntity(RAMUpdate update, RAM entity) {
    }

    @Override
    public void beforeAdd(RAMCreate create) {
        if (genericsRepository.isExistName(create.getTen().trim()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }

    @Override
    public void beforeUpdate(RAMUpdate update) {
        if (genericsRepository.isExistNameAndDifferentId(update.getTen().trim(), update.getId()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }
}
