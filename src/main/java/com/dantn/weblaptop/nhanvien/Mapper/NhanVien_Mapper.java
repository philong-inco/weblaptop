package com.dantn.weblaptop.nhanvien.Mapper;


import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.nhanvien.Dto.Request.CreateNhanVien_Request;
import com.dantn.weblaptop.nhanvien.Dto.Request.UpdateNhanVien_Request;
import com.dantn.weblaptop.nhanvien.Dto.Response.NhanVien_Response;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NhanVien_Mapper {
    NhanVien_Mapper INSTANCE = Mappers.getMapper(NhanVien_Mapper.class);

    @Mapping(source = "soCanCuocCongDan", target = "cccd")
    @Mapping(source = "ngaySinh", target = "ngaySinh")
    @Mapping(source = "accountStatus", target = "trangThai")
    @Mapping(source = "idVaiTro", target = "nhanVienVaiTros")
    NhanVien toEntity(CreateNhanVien_Request dto);

    @Mapping(source = "soCanCuocCongDan", target = "cccd")
    @Mapping(source = "ngaySinh", target = "ngaySinh")
    @Mapping(source = "accountStatus", target = "trangThai")
    @Mapping(source = "vaiTroList", target = "nhanVienVaiTros")
    NhanVien toEntity(UpdateNhanVien_Request dto);

    @Mapping(source = "cccd", target = "soCanCuocCongDan")
    @Mapping(source = "ngaySinh", target = "ngaySinh")
    @Mapping(source = "trangThai", target = "accountStatus")
    @Mapping(source = "nhanVienVaiTros", target = "vaiTroList")
    NhanVien_Response toResponse(NhanVien entity);
}

