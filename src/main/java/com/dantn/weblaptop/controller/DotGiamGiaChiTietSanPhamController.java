package com.dantn.weblaptop.controller;//package com.dantn.weblaptop.dotgiamgia.controller;
//
//import com.dantn.weblaptop.dto.DotGiamGiaSanPhamChiTietDTO;
//import com.dantn.weblaptop.dotgiamgia.model.mapper.DotGiamGiaChiTietSanPhamMapper;
//import com.dantn.weblaptop.dotgiamgia.model.request.CreateDotGiamGiaSanPhamChiTietRequest;
//import com.dantn.weblaptop.dotgiamgia.model.request.UpdateDotGiamGiaSanPhamChiTietRequest;
//import com.dantn.weblaptop.dotgiamgia.service.DotGiamGiaSanPhamChiTietService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@CrossOrigin(origins = "*", maxAge = -1)
//@RequestMapping("/api/v1")
//public class DotGiamGiaChiTietSanPhamController {
//
//    private final DotGiamGiaSanPhamChiTietService dotGiamGiaSanPhamChiTietService;
//    private final DotGiamGiaChiTietSanPhamMapper mapper;
//
//    public DotGiamGiaChiTietSanPhamController(DotGiamGiaSanPhamChiTietService dotGiamGiaSanPhamChiTietService, DotGiamGiaChiTietSanPhamMapper mapper) {
//        this.dotGiamGiaSanPhamChiTietService = dotGiamGiaSanPhamChiTietService;
//        this.mapper = mapper;
//    }
//
//
//    @PostMapping("/sales/product-detail")
//    public void createDotGiamGiaChiTietSanPham(@RequestBody CreateDotGiamGiaSanPhamChiTietRequest request) {
//        dotGiamGiaSanPhamChiTietService.save(request);
//    }
//
//    @PutMapping("/update/salesl/{id}")
//    public void updateDotGiamGiaChiTietSanPham(@PathVariable("id") Long id, @RequestBody UpdateDotGiamGiaSanPhamChiTietRequest request) {
//        dotGiamGiaSanPhamChiTietService.update(id, request);
//    }
//
//    @GetMapping("/sales/product-detail/{id}")
//    public DotGiamGiaSanPhamChiTietDTO getDotGiamGiaChiTietSanPhamById(@PathVariable("id") Long id) {
//        DotGiamGiaSanPhamChiTietDTO dotGiamGiaSanPhamChiTietDTO = dotGiamGiaSanPhamChiTietService.findById(id);
//        return dotGiamGiaSanPhamChiTietDTO;
//    }
//
//    @GetMapping("/sales/product-detail")
//    public List<DotGiamGiaSanPhamChiTietDTO> getAllDotGiamGiaChiTietSanPham() {
//        return dotGiamGiaSanPhamChiTietService.findAll();
//    }
//
//    @DeleteMapping("/sales/product-detail/{id}")
//    public void deleteDotGiamGiaChiTietSanPham(@PathVariable("id") Long id) {
//        dotGiamGiaSanPhamChiTietService.delete(id);
//    }
//}
