package com.dantn.weblaptop.sanpham.thuoctinh.ram.mapper;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.RAM;
import com.dantn.weblaptop.sanpham.generics.GenericsMapper;
import com.dantn.weblaptop.sanpham.thuoctinh.ram.dto.request.RAMCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.ram.dto.request.RAMUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.ram.dto.response.RAMResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RamMapper extends GenericsMapper<RAM, RAMCreate, RAMUpdate, RAMResponse> {
    @Override
    public RAM createToEntity(RAMCreate create) {
        RAM ram = RAM.builder()
                .ten(create.getTen())
                .dungLuong(create.getDungLuong())
                .tocDoBus(create.getTocDoBus())
                .trangThai(create.getTrangThai())
                .build();
        return ram;
    }

    @Override
    public RAM updateToEntity(RAMUpdate update, RAM entity) {
        entity.setTen(update.getTen());
        entity.setDungLuong(update.getDungLuong());
        entity.setTocDoBus(update.getTocDoBus());
        entity.setTrangThai(update.getTrangThai());
        return entity;
    }

    @Override
    public RAMResponse entityToResponse(RAM entity) {
        RAMResponse response = RAMResponse.builder()
                .id(entity.getId())
                .ma(entity.getMa())
                .ten(entity.getTen())
                .trangThai(entity.getTrangThai())
                .dungLuong(entity.getDungLuong())
                .tocDoBus(entity.getTocDoBus())
                .ngayTao(entity.getNgayTao() + "")
                .ngaySua(entity.getNgaySua() + "")
                .nguoiTao(entity.getNguoiTao())
                .nguoiSua(entity.getNguoiSua())
                .build();
        response.convertTime();
        return response;
    }

    @Override
    public List<RAMResponse> listEntityToListResponse(List<RAM> entityList) {
        return entityList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<RAMResponse> pageEntityToPageResponse(Page<RAM> entityPage) {
        List<RAMResponse> list = entityPage.getContent().stream()
                .map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
