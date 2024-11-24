package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.response.NhuCauResponse;
import com.dantn.weblaptop.entity.sanpham.NhuCau;
import com.dantn.weblaptop.entity.sanpham.ThuongHieu;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.generics.IGenericsMapper;
import com.dantn.weblaptop.generics.IGenericsRepository;
import com.dantn.weblaptop.dto.request.create_request.ThuongHieuCreate;
import com.dantn.weblaptop.dto.request.update_request.ThuongHieuUpdate;
import com.dantn.weblaptop.dto.response.ThuongHieuResponse;
import com.dantn.weblaptop.service.specification.ThuongHieuSpecification;
import com.dantn.weblaptop.util.ConvertStringToArray;
import com.dantn.weblaptop.util.GenerateCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ThuongHieuService extends GenericsService<ThuongHieu, Long, ThuongHieuCreate, ThuongHieuUpdate, ThuongHieuResponse> {
    private final ThuongHieuSpecification specification;
    public ThuongHieuService(IGenericsRepository<ThuongHieu, Long> genericsRepository, IGenericsMapper<ThuongHieu, ThuongHieuCreate, ThuongHieuUpdate, ThuongHieuResponse> genericsMapper, ThuongHieuSpecification specification) {
        super(genericsRepository, genericsMapper);
        this.specification = specification;
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
    public Page<ThuongHieuResponse> findByFilter(String ten, String ma, String trangThai, Pageable pageable) {
        Specification<ThuongHieu> spec = Specification
                .where(specification.listStringLike("ten", ConvertStringToArray.toArray(ten)))
                .and(specification.listStringEquals("ma", ConvertStringToArray.toArray(ma)))
                .and(specification.listIntegerEquals("trangThai", ConvertStringToArray.toArray(trangThai)));
        Page<ThuongHieu> response = genericsRepository.findAll(spec, pageable);
        return genericsMapper.pageEntityToPageResponse(response);
    }
}
