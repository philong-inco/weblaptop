package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.request.create_request.FindSanPhamChiTietByFilter;
import com.dantn.weblaptop.dto.response.SanPhamChiTietClientDTO;
import com.dantn.weblaptop.dto.response.SanPhamClientDTO;
import com.dantn.weblaptop.entity.sanpham.SanPham;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import com.dantn.weblaptop.entity.sanpham.SerialNumber;
import com.dantn.weblaptop.generics.GenericsMapper;
import com.dantn.weblaptop.dto.request.create_request.SanPhamCreate;
import com.dantn.weblaptop.dto.request.update_request.SanPhamUpdate;
import com.dantn.weblaptop.dto.response.SanPhamResponse;
import com.dantn.weblaptop.repository.SanPhamChiTietRepository;
import com.dantn.weblaptop.repository.SerialNumberRepository;
import com.dantn.weblaptop.service.SanPhamChiTietService;
import com.dantn.weblaptop.util.ConvertStringToArray;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SanPhamMapper extends GenericsMapper<SanPham, SanPhamCreate, SanPhamUpdate, SanPhamResponse> {
    SerialNumberRepository repositorySeri;
    @Override
    public SanPham createToEntity(SanPhamCreate create) {
        SanPham sanPham = SanPham.builder()
                .ten(create.getTen())
                .moTa(create.getMoTa())
                .trangThai(create.getTrangThai())
                .build();
        return sanPham;
    }

    @Override
    public SanPham updateToEntity(SanPhamUpdate update, SanPham entity) {
        entity.setTen(update.getTen());
        entity.setMoTa(update.getMoTa());
        entity.setTrangThai(update.getTrangThai());
        return entity;
    }

    @Override
    public SanPhamResponse entityToResponse(SanPham entity) {
        SanPhamResponse response = SanPhamResponse.builder()
                .id(entity.getId())
                .ma(entity.getMa())
                .ten(entity.getTen())
                .moTa(entity.getMoTa())
                .trangThai(entity.getTrangThai())
                .nhuCau(entity.getNhuCau().getTen())
                .thuongHieu(entity.getThuongHieu().getTen())
                .ngayTao(entity.getNgayTao() + "")
                .ngaySua(entity.getNgaySua() + "")
                .nguoiTao(entity.getNguoiTao())
                .nguoiSua(entity.getNguoiSua())
                .build();
        List<SerialNumber> listSeri = repositorySeri.findSerialNumberBySanPhamId(entity.getId());
        if (listSeri != null && listSeri.size() > 0){
            response.setSoLuong(listSeri.size()+"");
        } else {
            response.setSoLuong("0");
        }

        response.convertTime();
        return response;
    }

    @Override
    public List<SanPhamResponse> listEntityToListResponse(List<SanPham> entityList) {
        return entityList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<SanPhamResponse> pageEntityToPageResponse(Page<SanPham> entityPage) {
        List<SanPhamResponse> list = entityPage.getContent().stream()
                .map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }

    public SanPhamClientDTO entityToClient(SanPham entity){
       SanPhamClientDTO client = SanPhamClientDTO.builder()
               .id(entity.getId())
               .ten(entity.getTen())
               .moTa(entity.getMoTa())
               .nhuCau(entity.getNhuCau().getTen())
               .thuongHieu(entity.getThuongHieu().getTen())
               .build();

       return client;
    }

    public Page<SanPhamClientDTO> pageEntityToClient(Page<SanPham> entityPage){
        List<SanPhamClientDTO> list = entityPage.getContent().stream()
                .map(this::entityToClient).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
