package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.FindRamFilter;
import com.dantn.weblaptop.dto.response.RAMResponse;
import com.dantn.weblaptop.entity.sanpham.NhuCau;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.RAM;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.generics.IGenericsMapper;
import com.dantn.weblaptop.generics.IGenericsRepository;
import com.dantn.weblaptop.dto.request.create_request.NhuCauCreate;
import com.dantn.weblaptop.dto.request.update_request.NhuCauUpdate;
import com.dantn.weblaptop.dto.response.NhuCauResponse;
import com.dantn.weblaptop.service.specification.NhuCauSpecification;
import com.dantn.weblaptop.util.ConvertStringToArray;
import com.dantn.weblaptop.util.GenerateCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class NhuCauService extends GenericsService<NhuCau, Long, NhuCauCreate, NhuCauUpdate, NhuCauResponse> {
    private final NhuCauSpecification specification;
    public NhuCauService(IGenericsRepository<NhuCau, Long> genericsRepository, IGenericsMapper<NhuCau, NhuCauCreate, NhuCauUpdate, NhuCauResponse> genericsMapper, NhuCauSpecification specification) {
        super(genericsRepository, genericsMapper);
        this.specification = specification;
    }

    @Override
    public void convertCreateToEntity(NhuCauCreate create, NhuCau entity) {
        String ma;
        do {
            ma = GenerateCode.generateCode(GenerateCode.NHU_CAU_PREFIX);
        } while (existByCode(ma));
        entity.setMa(ma);
    }

    @Override
    public void convertUpdateToEntity(NhuCauUpdate update, NhuCau entity) {

    }

    @Override
    public void beforeAdd(NhuCauCreate create) {
        if (genericsRepository.isExistName(create.getTen().trim()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }

    @Override
    public void beforeUpdate(NhuCauUpdate update) {
        if (genericsRepository.isExistNameAndDifferentId(update.getTen().trim(), update.getId()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }
    public Page<NhuCauResponse> findByFilter(String ten, String ma, String trangThai, Pageable pageable) {
        Specification<NhuCau> spec = Specification
                .where(specification.listStringLike("ten", ConvertStringToArray.toArray(ten)))
                .and(specification.listStringEquals("ma", ConvertStringToArray.toArray(ma)))
                .and(specification.listIntegerEquals("trangThai", ConvertStringToArray.toArray(trangThai)));
        Page<NhuCau> response = genericsRepository.findAll(spec, pageable);
        return genericsMapper.pageEntityToPageResponse(response);
    }
}
