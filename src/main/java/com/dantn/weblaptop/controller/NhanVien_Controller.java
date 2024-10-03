package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.ChangeEmail_Dto;
import com.dantn.weblaptop.dto.ForgotPassword_Dto;
import com.dantn.weblaptop.dto.request.create_request.CreateNhanVien;
import com.dantn.weblaptop.dto.request.update_request.UpdateNhanVien;
import com.dantn.weblaptop.repository.NhanVien_Repositoy;
import com.dantn.weblaptop.service.NhanVien_Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/nhan_vien")
@RestController
@Component
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class NhanVien_Controller {

    @Qualifier("nhanVien_Service")
    private final NhanVien_Service nhanVienService;
    private final NhanVien_Repositoy nhanVien_Repositoy;


    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(nhanVienService.pageNhanVien(pageNo, pageSize));
    }

    @GetMapping("/danhsachnhanvien")
    public ResponseEntity<?> getDanhSach() {
        return ResponseEntity.ok(nhanVienService.getDanhSachNhanVien());
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search", required = false) String search,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(nhanVienService.pageSearchNhanVien(pageNo, pageSize, search));
    }

    @GetMapping("/searchtrangthai")
    public ResponseEntity<?> searchTrangThai(@RequestParam(name = "trangThai", required = false) Integer trangThai,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(nhanVienService.pageSearchTrangThaiNhanVien(pageNo, pageSize, trangThai));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNhanVienId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(nhanVienService.getOne(id));
    }

    @GetMapping("/searchgioitinh")
    public ResponseEntity<?> searchGioiTinh(@RequestParam(name = "gioiTinh", required = false) Integer gioiTinh,
                                             @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                             @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(nhanVienService.searchNhanVienByGioiTinh(pageNo, pageSize, gioiTinh));
    }

    @GetMapping("/searchyear")
    public ResponseEntity<?> searchNamSinh(@RequestParam(name = "year", required = false) Integer year,
                                            @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                            @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(nhanVienService.searchNhanVienByNamSinh(pageNo, pageSize, year));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNhanVien(@Valid @RequestBody CreateNhanVien createNhanVien, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(nhanVienService.create(createNhanVien));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNhanVien(@Valid @RequestBody UpdateNhanVien updateNhanVienRequest, BindingResult result, @PathVariable("id") Long id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(nhanVienService.update(updateNhanVienRequest, id));
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeNhanVien(@PathVariable("id") Long id) {
        nhanVienService.removeOrRevert(id);
        return ResponseEntity.ok("Remove Success");
    }
    @PutMapping("/rollBackStatus/{id}")
    public ResponseEntity<?> rollBackStatusNhanVien(@PathVariable("id") Long id) {
        nhanVienService.rollBackStatusNhanVien(id);
        return ResponseEntity.ok("Remove Success");
    }

    @PostMapping("/changeEmailNv")
    public ResponseEntity<String> changeEmail(@RequestBody ChangeEmail_Dto changeEmailDto, @RequestParam String newEmail) {
        boolean isChanged = nhanVienService.changeEmail(changeEmailDto, newEmail);
        if (isChanged) {
            return ResponseEntity.ok("Thay đổi email thành công!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vui lòng nhập lại Email của mình");
        }
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> sendForgotPasswordEmailForNhanVien(@RequestBody ForgotPassword_Dto forgotPasswordDto) {
        try {
            nhanVienService.sendForgotPasswordEmailForNhanVien(forgotPasswordDto.getEmail());
            return ResponseEntity.ok("Mật khẩu đã được gửi lại vào email của nhân viên.");
        } catch (ConfigDataResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/updatepassword/{email}")
    public ResponseEntity<?> updatePassword(@PathVariable("email") String email, @RequestParam("newPassword") String newPassword) {
        this.nhanVienService.updatePassword(newPassword, email);
        return ResponseEntity.ok("Password was updated");
    }

    @PutMapping("/updateimage/{id}")
    public ResponseEntity<?> updateImage(@PathVariable("id") Long id, @RequestParam("image") String image) {
        this.nhanVienService.updateImageNV(image, id);
        return ResponseEntity.ok("Image was updated");
    }
}
