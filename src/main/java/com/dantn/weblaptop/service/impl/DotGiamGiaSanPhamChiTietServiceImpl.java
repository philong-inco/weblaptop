//package com.dantn.weblaptop.dotgiamgia.service.impl;
//
//import com.dantn.weblaptop.dto.DotGiamGiaSanPhamChiTietDTO;
//import com.dantn.weblaptop.dotgiamgia.model.mapper.DotGiamGiaChiTietSanPhamMapper;
//import com.dantn.weblaptop.dotgiamgia.model.request.CreateDotGiamGiaSanPhamChiTietRequest;
//import com.dantn.weblaptop.dotgiamgia.model.request.UpdateDotGiamGiaSanPhamChiTietRequest;
//import com.dantn.weblaptop.dotgiamgia.repository.DotGiamGiaChiTietSanPhamRepository;
//import com.dantn.weblaptop.repository.DotGiamGiaRepository;
//import com.dantn.weblaptop.dotgiamgia.service.DotGiamGiaSanPhamChiTietService;
//import com.dantn.weblaptop.service.DotGiamGiaService;
//import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
//import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGiaSanPhamChiTiet;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class DotGiamGiaSanPhamChiTietServiceImpl implements DotGiamGiaSanPhamChiTietService {
//    @Autowired
//    private DotGiamGiaChiTietSanPhamRepository dotGiamGiaChiTietSanPhamRepository;
//    @Autowired
//    private final DotGiamGiaChiTietSanPhamMapper dotGiamGiaChiTietSanPhamMapper;
//
//    @Autowired
//    public DotGiamGiaSanPhamChiTietServiceImpl(DotGiamGiaChiTietSanPhamMapper dotGiamGiaChiTietSanPhamMapper) {
//        this.dotGiamGiaChiTietSanPhamMapper = dotGiamGiaChiTietSanPhamMapper;
//    }
//
//    @Override
//    public List<DotGiamGiaSanPhamChiTietDTO> findAll() {
//        List<DotGiamGiaSanPhamChiTiet> dotGiamGiaSanPhamChiTietList = dotGiamGiaChiTietSanPhamRepository.findAll();
//        List<DotGiamGiaSanPhamChiTietDTO> listDotGiamGiaSanPhamChiTietDTO = dotGiamGiaChiTietSanPhamMapper.listDotGiamGiaSanPhamChiTietToListDotGiamGiaSanPhamChiTietDTO(dotGiamGiaSanPhamChiTietList);
//        return listDotGiamGiaSanPhamChiTietDTO;
//    }
//
//    @Override
//    public DotGiamGiaSanPhamChiTietDTO findById(Long id) {
//        DotGiamGiaSanPhamChiTiet dotGiamGiaSanPhamChiTiet = dotGiamGiaChiTietSanPhamRepository.findById(id)
//                .orElseThrow(() -> {
//                    throw new RuntimeException("ID not found");
//                });
//        DotGiamGiaSanPhamChiTietDTO dotGiamGiaSanPhamChiTietDTO = dotGiamGiaChiTietSanPhamMapper.dotGiamGiaSanPhamChiTietTodotGiamGiaSanPhamChiTietDTO(dotGiamGiaSanPhamChiTiet);
//        return dotGiamGiaSanPhamChiTietDTO;
//    }
//
//    @Override
//    public void save(CreateDotGiamGiaSanPhamChiTietRequest request) {
//        if (request == null) {
//            throw new RuntimeException("Thêm Đợt Giảm Giá Chi Tiết Sản Phẩm Không Thành Công !");
//        }
//        DotGiamGiaSanPhamChiTiet giamGiaSanPhamChiTiet = dotGiamGiaChiTietSanPhamMapper.createDotGiamGiaChiTietSanPham(request);
//        dotGiamGiaChiTietSanPhamRepository.save(giamGiaSanPhamChiTiet);
//    }
//
//    @Override
//    public void update(Long id, UpdateDotGiamGiaSanPhamChiTietRequest request) {
//        DotGiamGiaSanPhamChiTiet giamGiaSanPhamChiTiet = dotGiamGiaChiTietSanPhamRepository.findById(id).orElseThrow(
//                () -> new RuntimeException("id not found"));
//        dotGiamGiaChiTietSanPhamMapper.updateDotGiamGiaChiTietSanPham(giamGiaSanPhamChiTiet, request);
//        dotGiamGiaChiTietSanPhamRepository.save(giamGiaSanPhamChiTiet);
//
//    }
//
//    @Override
//    public void delete(Long id) {
//        DotGiamGiaSanPhamChiTiet dotGiamGiaDB = dotGiamGiaChiTietSanPhamRepository.findById(id).orElseThrow(
//                () -> new RuntimeException("id not found"));
//        dotGiamGiaChiTietSanPhamRepository.deleteById(id);
//    }
//}
