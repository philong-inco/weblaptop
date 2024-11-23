package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.response.MauSacResponse;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.CPU;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.MauSac;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.generics.IGenericsMapper;
import com.dantn.weblaptop.generics.IGenericsRepository;
import com.dantn.weblaptop.dto.request.create_request.CPUCreate;
import com.dantn.weblaptop.dto.request.update_request.CPUUpdate;
import com.dantn.weblaptop.dto.response.CPUResponse;
import com.dantn.weblaptop.service.specification.CPUSpecification;
import com.dantn.weblaptop.util.ConvertStringToArray;
import com.dantn.weblaptop.util.GenerateCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CPUService extends GenericsService<CPU, Long, CPUCreate, CPUUpdate, CPUResponse> {
    private final CPUSpecification specification;
    public CPUService(IGenericsRepository<CPU, Long> genericsRepository, IGenericsMapper<CPU, CPUCreate, CPUUpdate, CPUResponse> genericsMapper, CPUSpecification specification) {
        super(genericsRepository, genericsMapper);
        this.specification = specification;
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
    public Page<CPUResponse> findByFilter(String ten, String ma, String trangThai, Pageable pageable) {
        Specification<CPU> spec = Specification
                .where(specification.listStringLike("ten", ConvertStringToArray.toArray(ten)))
                .and(specification.listStringEquals("ma", ConvertStringToArray.toArray(ma)))
                .and(specification.listIntegerEquals("trangThai", ConvertStringToArray.toArray(trangThai)));
        Page<CPU> response = genericsRepository.findAll(spec, pageable);
        return genericsMapper.pageEntityToPageResponse(response);
    }
}
