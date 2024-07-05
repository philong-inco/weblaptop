package com.dantn.weblaptop.sanpham.thuoctinh.cpu.service;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.CPU;
import com.dantn.weblaptop.sanpham.generics.GenericsService;
import com.dantn.weblaptop.sanpham.generics.IGenericsMapper;
import com.dantn.weblaptop.sanpham.generics.IGenericsRepository;
import com.dantn.weblaptop.sanpham.thuoctinh.cpu.dto.request.CPUCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.cpu.dto.request.CPUUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.cpu.dto.response.CPUResponse;
import com.dantn.weblaptop.sanpham.util.GenerateCode;
import org.springframework.stereotype.Service;

@Service
public class CPUService extends GenericsService<CPU, Long, CPUCreate, CPUUpdate, CPUResponse> {

    public CPUService(IGenericsRepository<CPU, Long> genericsRepository, IGenericsMapper<CPU, CPUCreate, CPUUpdate, CPUResponse> genericsMapper) {
        super(genericsRepository, genericsMapper);
    }

    @Override
    public void convertCreateToEntity(CPUCreate create, CPU entity) {
        String ma;
        do {
            ma = GenerateCode.generateCode(GenerateCode.CPU_PREFIX);
        } while (existByCode(ma));
        entity.setMa(ma);
    }

    @Override
    public void convertUpdateToEntity(CPUUpdate update, CPU entity) {

    }

    @Override
    public void beforeAdd(CPUCreate create) {
        if (genericsRepository.isExistName(create.getTen().trim()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }

    @Override
    public void beforeUpdate(CPUUpdate update) {
        if (genericsRepository.isExistNameAndDifferentId(update.getTen().trim(), update.getId()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }
}
