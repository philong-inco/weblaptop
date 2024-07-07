package com.dantn.weblaptop.mapper;

import com.dantn.weblaptop.dto.request.create_request.CreateNhanVien;
import com.dantn.weblaptop.dto.request.update_request.UpdateNhanVien;
import com.dantn.weblaptop.dto.response.NhanVienResponse;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;

import java.util.List;

public interface NhanVien_Mapper {
    NhanVien CreateToEntity(CreateNhanVien dto);


    NhanVien UpdateToEntity(UpdateNhanVien dto);


    NhanVienResponse EntiyToResponse(NhanVien entity);

    List<NhanVienResponse> listNhanVienEntityToNhanVienResponse(List<NhanVien> nhanVienList);
}
