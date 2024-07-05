package com.dantn.weblaptop.sanpham.thuoctinh.webcam.service;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.Webcam;
import com.dantn.weblaptop.sanpham.generics.GenericsService;
import com.dantn.weblaptop.sanpham.generics.IGenericsMapper;
import com.dantn.weblaptop.sanpham.generics.IGenericsRepository;
import com.dantn.weblaptop.sanpham.thuoctinh.webcam.dto.request.WebcamCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.webcam.dto.request.WebcamUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.webcam.dto.response.WebcamResponse;
import com.dantn.weblaptop.sanpham.util.GenerateCode;
import org.springframework.stereotype.Service;

@Service
public class WebcamService extends GenericsService<Webcam, Long, WebcamCreate, WebcamUpdate, WebcamResponse> {
    public WebcamService(IGenericsRepository<Webcam, Long> genericsRepository,
                         IGenericsMapper<Webcam, WebcamCreate, WebcamUpdate, WebcamResponse> genericsMapper) {
        super(genericsRepository, genericsMapper);
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
}
