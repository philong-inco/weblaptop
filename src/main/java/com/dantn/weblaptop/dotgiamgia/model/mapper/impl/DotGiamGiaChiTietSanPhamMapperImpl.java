package com.dantn.weblaptop.dotgiamgia.model.mapper.impl;

import com.dantn.weblaptop.dotgiamgia.dto.DotGiamGiaDTO;
import com.dantn.weblaptop.dotgiamgia.dto.DotGiamGiaSanPhamChiTietDTO;
import com.dantn.weblaptop.dotgiamgia.dto.SanPhamChiTietDTO;
import com.dantn.weblaptop.dotgiamgia.model.mapper.DotGiamGiaChiTietSanPhamMapper;
import com.dantn.weblaptop.dotgiamgia.model.request.CreateDotGiamGiaSanPhamChiTietRequest;
import com.dantn.weblaptop.dotgiamgia.model.request.UpdateDotGiamGiaSanPhamChiTietRequest;
import com.dantn.weblaptop.dotgiamgia.repository.DotGiamGiaChiTietSanPhamRepository;
import com.dantn.weblaptop.dotgiamgia.service.DotGiamGiaService;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGiaSanPhamChiTiet;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class DotGiamGiaChiTietSanPhamMapperImpl implements DotGiamGiaChiTietSanPhamMapper {
    private final DotGiamGiaChiTietSanPhamRepository dotGiamGiaChiTietSanPhamRepository;
    private final DotGiamGiaService dotGiamGiaService;

    @Autowired
    public DotGiamGiaChiTietSanPhamMapperImpl(DotGiamGiaChiTietSanPhamRepository repository,
                                              DotGiamGiaService dotGiamGiaService) {
        this.dotGiamGiaChiTietSanPhamRepository = repository;
        this.dotGiamGiaService = dotGiamGiaService;
    }


    @Override
    public List<DotGiamGiaSanPhamChiTietDTO> listDotGiamGiaSanPhamChiTietToListDotGiamGiaSanPhamChiTietDTO(List<DotGiamGiaSanPhamChiTiet> dotGiamGiaSanPhamChiTietList) {
        List<DotGiamGiaSanPhamChiTietDTO> listDTO = new ArrayList<>();
        for (DotGiamGiaSanPhamChiTiet dotGiamGiaSanPhamChiTiet : dotGiamGiaSanPhamChiTietList) {
            SanPhamChiTietDTO sanPhamChiTietDTO = new SanPhamChiTietDTO();
            SanPhamChiTiet spct = this.dotGiamGiaChiTietSanPhamRepository.findSanPhamChiTietById(dotGiamGiaSanPhamChiTiet.getSanPhamChiTiet().getId());
            sanPhamChiTietDTO.setId(spct.getId());
            sanPhamChiTietDTO.setGiaBan(spct.getGiaBan());
            sanPhamChiTietDTO.setTrangThai(spct.getTrangThai());
            DotGiamGia dotGiamGia = dotGiamGiaService.findById(dotGiamGiaSanPhamChiTiet.getDotGiamGia().getId());
            DotGiamGiaDTO dotGiamGiaDTO = new DotGiamGiaDTO();
            dotGiamGiaDTO.setId(dotGiamGia.getId());
            dotGiamGiaDTO.setMa(dotGiamGia.getMa());
            dotGiamGiaDTO.setLoaiChietKhau(dotGiamGia.getLoaiChietKhau());
            DotGiamGiaSanPhamChiTietDTO dotGiamGiaSanPhamChiTietDTO = new DotGiamGiaSanPhamChiTietDTO();
            dotGiamGiaSanPhamChiTietDTO.setId(dotGiamGiaSanPhamChiTiet.getId());
            dotGiamGiaSanPhamChiTietDTO.setDotGiamGia(dotGiamGiaDTO);
            dotGiamGiaSanPhamChiTietDTO.setSanPhamChiTiet(sanPhamChiTietDTO);
            dotGiamGiaSanPhamChiTietDTO.setTrangThai(dotGiamGiaSanPhamChiTiet.getTrangThai());
            listDTO.add(dotGiamGiaSanPhamChiTietDTO);
        }
        return listDTO;
    }

    @Override
    public DotGiamGiaSanPhamChiTietDTO dotGiamGiaSanPhamChiTietTodotGiamGiaSanPhamChiTietDTO(DotGiamGiaSanPhamChiTiet dotGiamGiaSanPhamChiTiet) {
        SanPhamChiTiet spct = this.dotGiamGiaChiTietSanPhamRepository.findSanPhamChiTietById(dotGiamGiaSanPhamChiTiet.getSanPhamChiTiet().getId());
        SanPhamChiTietDTO sanPhamChiTietDTO = new SanPhamChiTietDTO();
        sanPhamChiTietDTO.setId(spct.getId());
        sanPhamChiTietDTO.setGiaBan(spct.getGiaBan());
        sanPhamChiTietDTO.setTrangThai(spct.getTrangThai());
        DotGiamGia dotGiamGia = dotGiamGiaService.findById(dotGiamGiaSanPhamChiTiet.getDotGiamGia().getId());
        DotGiamGiaDTO dotGiamGiaDTO = new DotGiamGiaDTO();
        dotGiamGiaDTO.setId(dotGiamGia.getId());
        dotGiamGiaDTO.setMa(dotGiamGia.getMa());
        dotGiamGiaDTO.setLoaiChietKhau(dotGiamGia.getLoaiChietKhau());
        DotGiamGiaSanPhamChiTietDTO dotGiamGiaSanPhamChiTietDTO = new DotGiamGiaSanPhamChiTietDTO();
        dotGiamGiaSanPhamChiTietDTO.setId(dotGiamGiaSanPhamChiTiet.getId());
        dotGiamGiaSanPhamChiTietDTO.setDotGiamGia(dotGiamGiaDTO);
        dotGiamGiaSanPhamChiTietDTO.setSanPhamChiTiet(sanPhamChiTietDTO);
        dotGiamGiaSanPhamChiTietDTO.setTrangThai(dotGiamGiaSanPhamChiTiet.getTrangThai());
        return dotGiamGiaSanPhamChiTietDTO;

    }

    @Override
    @Transactional
    public DotGiamGiaSanPhamChiTiet createDotGiamGiaChiTietSanPham(CreateDotGiamGiaSanPhamChiTietRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        SanPhamChiTiet sanPhamChiTiet = dotGiamGiaChiTietSanPhamRepository.findSanPhamChiTietById(request.getSanPhamChiTiet());
        if (sanPhamChiTiet == null) {
            throw new IllegalArgumentException("SanPhamChiTiet not found with id: " + request.getDotGiamGia());
        }

        DotGiamGia dotGiamGia = dotGiamGiaService.findById(request.getDotGiamGia());
        if (dotGiamGia == null) {
            throw new IllegalArgumentException("DotGiamGia not found with id: " + request.getDotGiamGia());
        }

        DotGiamGiaSanPhamChiTiet dotGiamGiaSanPhamChiTiet = new DotGiamGiaSanPhamChiTiet();
        dotGiamGiaSanPhamChiTiet.setDotGiamGia(dotGiamGia);
        dotGiamGiaSanPhamChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
        dotGiamGiaSanPhamChiTiet.setTrangThai(request.getTrangThai());
        return dotGiamGiaSanPhamChiTiet;
    }

    @Override
    @Transactional
    public void updateDotGiamGiaChiTietSanPham(DotGiamGiaSanPhamChiTiet dotGiamGiaSanPhamChiTiet, UpdateDotGiamGiaSanPhamChiTietRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        SanPhamChiTiet sanPhamChiTiet = dotGiamGiaChiTietSanPhamRepository.findSanPhamChiTietById(request.getSanPhamChiTiet());
        if (sanPhamChiTiet == null) {
            throw new IllegalArgumentException("SanPhamChiTiet not found with id: " + request.getSanPhamChiTiet());
        }

        DotGiamGia dotGiamGia = dotGiamGiaService.findById(request.getDotGiamGia());
        if (dotGiamGia == null) {
            throw new IllegalArgumentException("DotGiamGia not found with id: " + request.getSanPhamChiTiet());
        }
        dotGiamGiaSanPhamChiTiet.setDotGiamGia(dotGiamGia);
        dotGiamGiaSanPhamChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
        dotGiamGiaSanPhamChiTiet.setTrangThai(request.getTrangThai());
    }
}
