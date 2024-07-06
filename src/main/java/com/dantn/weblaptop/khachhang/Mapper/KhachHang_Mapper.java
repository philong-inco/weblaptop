//package com.dantn.weblaptop.khachhang.Mapper;
//
//import com.dantn.weblaptop.entity.nhanvien.NhanVien;
//import com.dantn.weblaptop.nhanvien.Dto.Request.CreateNhanVien_Request;
//import com.dantn.weblaptop.nhanvien.Dto.Request.UpdateNhanVien_Request;
//import com.dantn.weblaptop.nhanvien.Dto.Response.NhanVien_Response;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//@Mapper(componentModel = "spring")
//public interface KhachHang_Mapper {
//
//    NhanVien toEntity(CreateNhanVien_Request createNhanVienRequest);
//
//    NhanVien toEntity(UpdateNhanVien_Request updateNhanVienRequest);
//
//    NhanVien_Response toResponseDTO(NhanVien nhanVien);
//
//    @Mapping(target = "id", ignore = true)
//    UpdateNhanVien_Request toUpdateDTO(NhanVien nhanVien);
//
//    @Mapping(target = "roles", ignore = true)
//    CreateNhanVien_Request toCreateDTO(NhanVien nhanVien);
//}
