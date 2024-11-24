package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.response.RAMResponse;
import com.dantn.weblaptop.dto.response.ThuongHieuResponse;
import com.dantn.weblaptop.entity.sanpham.ThuongHieu;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.RAM;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.VGA;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.generics.IGenericsMapper;
import com.dantn.weblaptop.generics.IGenericsRepository;
import com.dantn.weblaptop.dto.request.create_request.VGACreate;
import com.dantn.weblaptop.dto.request.update_request.VGAUpdate;
import com.dantn.weblaptop.dto.response.VGAResponse;
import com.dantn.weblaptop.service.specification.VGASpecification;
import com.dantn.weblaptop.util.ConvertStringToArray;
import com.dantn.weblaptop.util.GenerateCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class VGAService extends GenericsService<VGA, Long, VGACreate, VGAUpdate, VGAResponse> {
    private final VGASpecification specification;
    public VGAService(IGenericsRepository<VGA, Long> genericsRepository, IGenericsMapper<VGA, VGACreate, VGAUpdate, VGAResponse> genericsMapper, VGASpecification specification) {
        super(genericsRepository, genericsMapper);
        this.specification = specification;
    }

    @Override
    public void convertCreateToEntity(VGACreate create, VGA entity) {
        String ma;
        do {
            ma = GenerateCode.generateCode(GenerateCode.VGA_PREFIX);
        } while (existByCode(ma));
        entity.setMa(ma);
    }

    @Override
    public void convertUpdateToEntity(VGAUpdate update, VGA entity) {
    }

    @Override
    public void beforeAdd(VGACreate create) {
        if (genericsRepository.isExistName(create.getTen().trim()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }

    @Override
    public void beforeUpdate(VGAUpdate update) {
        if (genericsRepository.isExistNameAndDifferentId(update.getTen().trim(), update.getId()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }
    public Page<VGAResponse> findByFilter(String ten, String ma, String trangThai, Pageable pageable) {
        Specification<VGA> spec = Specification
                .where(specification.listStringLike("ten", ConvertStringToArray.toArray(ten)))
                .and(specification.listStringEquals("ma", ConvertStringToArray.toArray(ma)))
                .and(specification.listIntegerEquals("trangThai", ConvertStringToArray.toArray(trangThai)));
        Page<VGA> response = genericsRepository.findAll(spec, pageable);
        return genericsMapper.pageEntityToPageResponse(response);
    }
}
