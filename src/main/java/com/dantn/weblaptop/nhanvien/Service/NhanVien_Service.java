//package com.dantn.weblaptop.nhanvien.Service;
//
//import com.dantn.weblaptop.constant.AccountStatus;
//import com.dantn.weblaptop.nhanvien.Dto.Request.CreateNhanVien_Request;
//import com.dantn.weblaptop.nhanvien.Dto.Request.UpdateNhanVien_Request;
//import com.dantn.weblaptop.nhanvien.Dto.Response.NhanVien_Response;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.List;
//
//@Service
//public interface NhanVien_Service {
//    Page<NhanVien_Response> pageNhanVien(Integer pageNo, Integer size);
//
//    Page<NhanVien_Response> pageSearchNhanVien(Integer pageNo, Integer size, String search, AccountStatus trangThai, Integer vaiTroId);
//
//    List<NhanVien_Response> listNhanVienResponse();
//    //    List<NhanVienInfo> listEmailAndSdtAndCccdNhanVien();
//    NhanVien_Response findByEmail(String email);
//
//    byte[] exportExcelNhanVien() throws IOException;
//
//    NhanVien_Response create(CreateNhanVien_Request createNhanVienRequest);
//
//    NhanVien_Response update(UpdateNhanVien_Request updateNhanVienRequest, Integer id);
//
//    NhanVien_Response getOne(Integer id);
//
//    NhanVien_Response getOneNhanVienByEmail(String email);
//
//    void removeOrRevert(String trangThaiNhanVien, Integer id);
//
//    boolean changeEmail(UpdateNhanVien_Request updateNhanVienRequest, String newEmail);
//
//    void sendForgotPasswordEmailForNhanVien(String email);
//
//    void updatePassword(String newPassword, String email);
//
//    void updateImageNV(String image, String email);
//}
//
