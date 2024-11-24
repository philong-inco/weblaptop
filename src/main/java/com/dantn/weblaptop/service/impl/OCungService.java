package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.response.ThuongHieuResponse;
import com.dantn.weblaptop.entity.sanpham.ThuongHieu;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.OCung;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.generics.IGenericsMapper;
import com.dantn.weblaptop.generics.IGenericsRepository;
import com.dantn.weblaptop.dto.request.create_request.OCungCreate;
import com.dantn.weblaptop.dto.request.update_request.OCungUpdate;
import com.dantn.weblaptop.dto.response.OCungResponse;
import com.dantn.weblaptop.service.specification.OCungSpecification;
import com.dantn.weblaptop.util.ConvertStringToArray;
import com.dantn.weblaptop.util.GenerateCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class OCungService extends GenericsService<OCung, Long, OCungCreate, OCungUpdate, OCungResponse> {
    private final OCungSpecification specification;
    public OCungService(IGenericsRepository<OCung, Long> genericsRepository, IGenericsMapper<OCung, OCungCreate, OCungUpdate, OCungResponse> genericsMapper, OCungSpecification specification) {
        super(genericsRepository, genericsMapper);
        this.specification = specification;
    }

    @Override
    public void convertCreateToEntity(OCungCreate create, OCung entity) {
        String ma;
        do {
            ma = GenerateCode.generateCode(GenerateCode.O_CUNG_PREFIX);
        } while (existByCode(ma));
        entity.setMa(ma);
    }

    @Override
    public void convertUpdateToEntity(OCungUpdate update, OCung entity) {

    }

    @Override
    public void beforeAdd(OCungCreate create) {
        if (genericsRepository.isExistName(create.getTen().trim()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }

    @Override
    public void beforeUpdate(OCungUpdate update) {
        if (genericsRepository.isExistNameAndDifferentId(update.getTen().trim(), update.getId()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }
    public Page<OCungResponse> findByFilter(String ten, String ma, String trangThai, Pageable pageable) {
        Specification<OCung> spec = Specification
                .where(specification.listStringLike("ten", ConvertStringToArray.toArray(ten)))
                .and(specification.listStringEquals("ma", ConvertStringToArray.toArray(ma)))
                .and(specification.listIntegerEquals("trangThai", ConvertStringToArray.toArray(trangThai)));
        Page<OCung> response = genericsRepository.findAll(spec, pageable);
        return genericsMapper.pageEntityToPageResponse(response);
    }
}
