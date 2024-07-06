package com.dantn.weblaptop.dotgiamgia.service.impl;

import com.dantn.weblaptop.dotgiamgia.model.request.create_request.CreateDotGiamGiaRequest;
import com.dantn.weblaptop.dotgiamgia.model.request.update_request.UpdateGotGiamGiaRequest;
import com.dantn.weblaptop.dotgiamgia.model.response.DotGiamGiaResponse;
import com.dantn.weblaptop.dotgiamgia.repository.DotGiamGiaRepository;
import com.dantn.weblaptop.dotgiamgia.model.mapper.DotGiamGiaMapper;
import com.dantn.weblaptop.dotgiamgia.service.DotGiamGiaService;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


@Component
public class DotGiamGiaServiceImpl implements DotGiamGiaService {
    @Autowired
    private DotGiamGiaRepository dotGiamGiaRepository;
    @Autowired
    private DotGiamGiaMapper dotGiamGiaMapper;

    @Override
    public Page<DotGiamGiaResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DotGiamGia> dotGiamGiaPage = dotGiamGiaRepository.finAllDotGiamGia(pageable);
        Page<DotGiamGiaResponse> responses = dotGiamGiaMapper.findAllRequestToDotGiamGiaResponse(dotGiamGiaPage);
        return responses;
    }

    @Override
    public DotGiamGiaResponse findById(Long id) {
        DotGiamGia dotGiamGia = dotGiamGiaRepository.findById(id).orElseThrow(() -> new RuntimeException("Đợt Giảm Giá Không Tồn Tại !"));
        DotGiamGiaResponse dotGiamGiaResponse = dotGiamGiaMapper.dotGiamGiaToDotGiamGiaResponse(dotGiamGia);
        return dotGiamGiaResponse;
    }

    @Override
    public DotGiamGiaResponse save(CreateDotGiamGiaRequest request) {
        DotGiamGia dotGiamGiaGetByName = dotGiamGiaRepository.findbyNameAndMa(request.getTen(), request.getMa());
        if (dotGiamGiaGetByName.getTen() != null) {
            throw new RuntimeException("Tên Hoặc Mã Đợt Giảm Giá : Đã Tồn Tại");
        }
        DotGiamGia dotGiamGia = dotGiamGiaMapper.createRequestToDotGiamGia(request);
        dotGiamGiaRepository.saveAndFlush(dotGiamGia);
        DotGiamGiaResponse dotGiamGiaResponse = dotGiamGiaMapper.dotGiamGiaToDotGiamGiaResponse(dotGiamGia);
        return dotGiamGiaResponse;
    }

    @Override
    public DotGiamGiaResponse update(Long id, UpdateGotGiamGiaRequest request) {
        DotGiamGia dotGiamGia = dotGiamGiaRepository.findById(id).orElseThrow(() -> new RuntimeException("Đợt Giảm Giá Không Tồn Tại"));
        DotGiamGia checkName = dotGiamGiaRepository.findByNameOrMaExcludingId(id, request.getTen(), request.getMa());
        if (checkName != null) {
            throw new RuntimeException(" Update Không Thành Công Tên Hoặc Ma Đợt Giảm Giá : Đã Tồn Tại");
        }
        dotGiamGia = dotGiamGiaMapper.updateDotGiamGia(dotGiamGia, request);
        dotGiamGiaRepository.save(dotGiamGia);
        DotGiamGiaResponse response = dotGiamGiaMapper.dotGiamGiaToDotGiamGiaResponse(dotGiamGia);
        return response;
    }
    //    @Override
//    public void update(Long id, UpdateDotGiamGiaRequest request) {
//        DotGiamGia dotGiamGia = dotGiamGiaRepository.findById(id).orElseThrow(() -> new RuntimeException("ID not found"));
//        dotGiamGia = dotGiamGiaMapper.updateDotGiamGia(dotGiamGia, request);
//        dotGiamGiaRepository.save(dotGiamGia);
//    }
//    @Autowired
//    private DotGiamGiaRepository dotGiamGiaRepository;
//    private DotGiamGiaMapper dotGiamGiaMapper = new DotGiamGiaMapperImpl();
//
//    @Override
//    public List<DotGiamGia> findAll() {
//        return dotGiamGiaRepository.findAll();
//    }
//
//    @Override
//    public DotGiamGia findById(Long id) {
//        return dotGiamGiaRepository.findById(id).orElseThrow(() -> {
//            throw new RuntimeException("Dot Giam gia id " + id + " not found");
//        });
//    }
//
//    @Override
//    public void save(CreateDotGiamGiaRequest request) {
//        if (request == null) {
//            throw new RuntimeException("Thêm Đợt Giảm Giá Không Thành Công !");
//        }
//        DotGiamGia dotGiamGia = dotGiamGiaMapper.createDotGiamGia(request);
//        System.out.println(" dotGiamGia :" + dotGiamGia.toString());
//        dotGiamGiaRepository.save(dotGiamGia);
//    }
//

//
//    @Override
//    public void delete(Long id) {
//        DotGiamGia dotGiamGiaDB = dotGiamGiaRepository.findById(id).orElseThrow(() -> new RuntimeException("id not found"));
//        if (dotGiamGiaDB.getId() != null) {
//            dotGiamGiaRepository.deleteById(id);
//        }
//    }
}
