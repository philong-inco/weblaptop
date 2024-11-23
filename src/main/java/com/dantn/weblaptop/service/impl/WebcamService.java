package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.response.ThuongHieuResponse;
import com.dantn.weblaptop.entity.sanpham.ThuongHieu;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.Webcam;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.generics.IGenericsMapper;
import com.dantn.weblaptop.generics.IGenericsRepository;
import com.dantn.weblaptop.dto.request.create_request.WebcamCreate;
import com.dantn.weblaptop.dto.request.update_request.WebcamUpdate;
import com.dantn.weblaptop.dto.response.WebcamResponse;
import com.dantn.weblaptop.service.specification.WebcamSpecification;
import com.dantn.weblaptop.util.ConvertStringToArray;
import com.dantn.weblaptop.util.GenerateCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class WebcamService extends GenericsService<Webcam, Long, WebcamCreate, WebcamUpdate, WebcamResponse> {
    private final WebcamSpecification specification;
    public WebcamService(IGenericsRepository<Webcam, Long> genericsRepository,
                         IGenericsMapper<Webcam, WebcamCreate, WebcamUpdate, WebcamResponse> genericsMapper, WebcamSpecification specification) {
        super(genericsRepository, genericsMapper);
        this.specification = specification;
    }

    @Override
    public void convertCreateToEntity(WebcamCreate create, Webcam entity) {
        String ma;
        do {
            ma = GenerateCode.generateCode(GenerateCode.WEBCAM_PREFIX);
        } while (existByCode(ma));
        entity.setMa(ma);
    }

    @Override
    public void convertUpdateToEntity(WebcamUpdate update, Webcam entity) {

    }

    @Override
    public void beforeAdd(WebcamCreate create) {
        if (genericsRepository.isExistName(create.getTen().trim()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }

    @Override
    public void beforeUpdate(WebcamUpdate update) {
        if (genericsRepository.isExistNameAndDifferentId(update.getTen().trim(), update.getId()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }
    public Page<WebcamResponse> findByFilter(String ten, String ma, String trangThai, Pageable pageable) {
        Specification<Webcam> spec = Specification
                .where(specification.listStringLike("ten", ConvertStringToArray.toArray(ten)))
                .and(specification.listStringEquals("ma", ConvertStringToArray.toArray(ma)))
                .and(specification.listIntegerEquals("trangThai", ConvertStringToArray.toArray(trangThai)));
        Page<Webcam> response = genericsRepository.findAll(spec, pageable);
        return genericsMapper.pageEntityToPageResponse(response);
    }
}
