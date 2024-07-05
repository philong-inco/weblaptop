package com.dantn.weblaptop.dotgiamgia.service.impl;

import com.dantn.weblaptop.dotgiamgia.repository.DotGiamGiaRepository;
import com.dantn.weblaptop.dotgiamgia.model.mapper.DotGiamGiaMapper;
import com.dantn.weblaptop.dotgiamgia.model.mapper.impl.DotGiamGiaMapperImpl;
import com.dantn.weblaptop.dotgiamgia.model.request.CreateDotGiamGiaRequest;
import com.dantn.weblaptop.dotgiamgia.model.request.UpdateDotGiamGiaRequest;
import com.dantn.weblaptop.dotgiamgia.service.DotGiamGiaService;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DotGiamGiaServiceImpl implements DotGiamGiaService {
    @Autowired
    private DotGiamGiaRepository dotGiamGiaRepository;
    private DotGiamGiaMapper dotGiamGiaMapper = new DotGiamGiaMapperImpl();

    @Override
    public List<DotGiamGia> findAll() {
        return dotGiamGiaRepository.findAll();
    }

    @Override
    public DotGiamGia findById(Long id) {
        return dotGiamGiaRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Dot Giam gia id " + id + " not found");
        });
    }

    @Override
    public void save(CreateDotGiamGiaRequest request) {
        if (request == null) {
            throw new RuntimeException("Thêm Đợt Giảm Giá Không Thành Công !");
        }
        DotGiamGia dotGiamGia = dotGiamGiaMapper.createDotGiamGia(request);
        System.out.println(" dotGiamGia :" + dotGiamGia.toString());
        dotGiamGiaRepository.save(dotGiamGia);
    }

    @Override
    public void update(Long id, UpdateDotGiamGiaRequest request) {
        DotGiamGia dotGiamGia = dotGiamGiaRepository.findById(id).orElseThrow(() -> new RuntimeException("ID not found"));
        dotGiamGia = dotGiamGiaMapper.updateDotGiamGia(dotGiamGia, request);
        dotGiamGiaRepository.save(dotGiamGia);
    }

    @Override
    public void delete(Long id) {
        DotGiamGia dotGiamGiaDB = dotGiamGiaRepository.findById(id).orElseThrow(() -> new RuntimeException("id not found"));
        if (dotGiamGiaDB.getId() != null) {
            dotGiamGiaRepository.deleteById(id);
        }
    }
}
