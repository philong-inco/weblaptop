package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.FindRamFilter;
import com.dantn.weblaptop.dto.request.create_request.RAMCreate;
import com.dantn.weblaptop.dto.request.update_request.RAMUpdate;
import com.dantn.weblaptop.dto.response.RAMResponse;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.RAM;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.generics.GenericsSpecificationAttributeInner;
import com.dantn.weblaptop.generics.IGenericsMapper;
import com.dantn.weblaptop.generics.IGenericsRepository;
import com.dantn.weblaptop.util.ConvertStringToArray;
import com.dantn.weblaptop.util.GenerateCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class RAMService extends GenericsService<RAM, Long, RAMCreate, RAMUpdate, RAMResponse> {

    private final GenericsSpecificationAttributeInner<RAM> ramSpecification;

    public RAMService(IGenericsRepository<RAM, Long> genericsRepository,
                      IGenericsMapper<RAM, RAMCreate, RAMUpdate, RAMResponse> genericsMapper, GenericsSpecificationAttributeInner<RAM> ramSpecification) {
        super(genericsRepository, genericsMapper);
        this.ramSpecification = ramSpecification;
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

    public Page<RAMResponse> findByFilter(FindRamFilter filter, Pageable pageable) {
        Specification<RAM> spec = Specification
                .where(ramSpecification.listStringLike("ten", ConvertStringToArray.toArray(filter.getName())))
                .and(ramSpecification.listStringEquals("ma", ConvertStringToArray.toArray(filter.getMa())))
                .and(ramSpecification.listStringLike("dungLuong", ConvertStringToArray.toArray(filter.getDungLuong())))
                .and(ramSpecification.listStringLike("tocDoBus", ConvertStringToArray.toArray(filter.getTocDoBus())))
                .and(ramSpecification.listIntegerEquals("trangThai", ConvertStringToArray.toArray(filter.getTrangThai())))
                .and(ramSpecification.timestampBefore("ngayTao", filter.getNgayTaoTruoc()))
                .and(ramSpecification.timestampAfter("ngayTao", filter.getNgayTaoSau()))
                .and(ramSpecification.timestampBefore("ngaySua", filter.getNgaySuaTruoc()))
                .and(ramSpecification.timestampAfter("ngaySua", filter.getNgaySuaSau()));

        Page<RAM> response = genericsRepository.findAll(spec, pageable);
        return genericsMapper.pageEntityToPageResponse(response);
    }
}
