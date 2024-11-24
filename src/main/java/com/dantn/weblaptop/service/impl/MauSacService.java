package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.response.ThuongHieuResponse;
import com.dantn.weblaptop.entity.sanpham.ThuongHieu;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.MauSac;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.generics.IGenericsMapper;
import com.dantn.weblaptop.generics.IGenericsRepository;
import com.dantn.weblaptop.dto.request.create_request.MauSacCreate;
import com.dantn.weblaptop.dto.request.update_request.MauSacUpdate;
import com.dantn.weblaptop.dto.response.MauSacResponse;
import com.dantn.weblaptop.service.specification.MauSacSpectification;
import com.dantn.weblaptop.util.ConvertStringToArray;
import com.dantn.weblaptop.util.GenerateCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class MauSacService extends GenericsService<MauSac, Long, MauSacCreate, MauSacUpdate, MauSacResponse> {
    private final MauSacSpectification specification;
    public MauSacService(IGenericsRepository<MauSac, Long> genericsRepository, IGenericsMapper<MauSac, MauSacCreate, MauSacUpdate, MauSacResponse> genericsMapper, MauSacSpectification specification) {
        super(genericsRepository, genericsMapper);
        this.specification = specification;
    }

    @Override
    public void convertCreateToEntity(MauSacCreate create, MauSac entity) {
        String ma;
        do {
            ma = GenerateCode.generateCode(GenerateCode.MAU_SAC_PREFIX);
        } while (existByCode(ma));
        entity.setMa(ma);
    }

    @Override
    public void convertUpdateToEntity(MauSacUpdate update, MauSac entity) {

    }

    @Override
    public void beforeAdd(MauSacCreate create) {
        if (genericsRepository.isExistName(create.getTen().trim()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }

    @Override
    public void beforeUpdate(MauSacUpdate update) {
        if (genericsRepository.isExistNameAndDifferentId(update.getTen().trim(), update.getId()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }
    public Page<MauSacResponse> findByFilter(String ten, String ma, String trangThai, Pageable pageable) {
        Specification<MauSac> spec = Specification
                .where(specification.listStringLike("ten", ConvertStringToArray.toArray(ten)))
                .and(specification.listStringEquals("ma", ConvertStringToArray.toArray(ma)))
                .and(specification.listIntegerEquals("trangThai", ConvertStringToArray.toArray(trangThai)));
        Page<MauSac> response = genericsRepository.findAll(spec, pageable);
        return genericsMapper.pageEntityToPageResponse(response);
    }
}
