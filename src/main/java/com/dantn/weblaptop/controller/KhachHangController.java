package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.InfomationKhachHang;
import com.dantn.weblaptop.dto.request.create_request.CreateKhachHang;
import com.dantn.weblaptop.dto.request.update_request.UpdateKhachHang;
import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.service.KhachHang_Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/khachhang")
@Component
@AllArgsConstructor
public class KhachHangController {

    @Qualifier("khachHang_Service")
    private final KhachHang_Service khachHangService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(khachHangService.pageKhachHang(pageNo, pageSize));
    }

    @GetMapping("/danhsachkhachhang")
    public ResponseEntity<?> danhSachKhachHang(){
        return ResponseEntity.ok(khachHangService.listKhachHang());
    }

    @GetMapping("/searchhangkhachhang")
    public ResponseEntity<?> getAll( @RequestParam(name = "hangKhachHang", required = false) Integer hangKhachHang,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(khachHangService.pageSearchHang(pageNo, pageSize, hangKhachHang));
    }

    @GetMapping("/searchgioitinh")
    public ResponseEntity<?> search(@RequestParam(name = "gioiTinh", required = false) Integer gioiTinh,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(khachHangService.pageSearchGioiTinh(pageNo, pageSize, gioiTinh));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search", required = false) String search,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(khachHangService.pageSearchKhachHang(pageNo, pageSize, search));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listKhachHangAcitve() {
        return ResponseEntity.ok(khachHangService.listKhachHangResponse());
    }

    @GetMapping("/list-email-sdt")
    public ResponseEntity<?> listEmailAndSdtKhachHang() {
        return ResponseEntity.ok(khachHangService.listKhachHangInfo());
    }

    @GetMapping("searchbyid/{id}")
    public ResponseEntity<?> getKhachHangId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(khachHangService.getOne(id));
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getKhachHangEmail(@PathVariable String email) {
        return ResponseEntity.ok(khachHangService.findKhachHangByEmail(email));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createKhachHang(@Valid @RequestBody CreateKhachHang createKhachHangRequest, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(khachHangService.create(createKhachHangRequest, request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateKhachHang(@Valid @RequestBody UpdateKhachHang updateKhachHangRequest, BindingResult result, @PathVariable("id") Long id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(khachHangService.update(updateKhachHangRequest, id));
    }


    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertKhachHang(@PathVariable("id") Long id) {
        khachHangService.removeOrRevert(1, id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeKhachHang(@PathVariable("id") Long id) {
        khachHangService.removeOrRevert(0, id);
        return ResponseEntity.ok("Remove Success");
    }

    @PutMapping("/update-info")
    public ResponseEntity<String> updatePersonalInfo(HttpSession session, @RequestBody InfomationKhachHang khachHangDto, @RequestHeader("Authorization") String authorization) {
        try {
            Long khachHangID;
            if (session.getAttribute("khachHangID") != null) {
                khachHangID = (Long) session.getAttribute("khachHangID");
            } else {
                khachHangID = Long.valueOf(authorization.substring(7));
                session.setAttribute("khachHangID", khachHangID);
            }
            boolean success = khachHangService.suaThongTin(khachHangDto, khachHangID);
            if (success) {
                return new ResponseEntity<>("Thông tin khách hàng đã được cập nhật thành công.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Không thể cập nhật thông tin khách hàng. Người dùng không tồn tại.", HttpStatus.NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Không thể xác định người dùng hiện tại.", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmailExit(@RequestParam String email) {
        try {
            boolean emailExists = khachHangService.checkMail(email);
            Map<String, Boolean> response = new HashMap<>();
            response.put("exists", emailExists);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Boolean> errorResponse = new HashMap<>();
            errorResponse.put("error", true);
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            Map<String, Boolean> errorResponse = new HashMap<>();
            errorResponse.put("error", true);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/change-email")
    public ResponseEntity<String> changeEmail(@RequestBody InfomationKhachHang khachHangDto, @RequestParam String newEmail) {
        boolean isChanged = khachHangService.changeEmail(khachHangDto, newEmail);
        if (isChanged) {
            return ResponseEntity.ok("Thay đổi email thành công!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vui lòng nhập lại mật khẩu của mình");
        }
    }

    @PutMapping("/update-image/{email}")
    public ResponseEntity<?> updateImage(@PathVariable("email") String email, @RequestParam("image") String image) {
        this.khachHangService.updateImage(image, email);
        return ResponseEntity.ok("Updated image");
    }

    @GetMapping("/countcustomerbydate")
    public ResponseEntity<Integer> countKhachHangByDate(
            @RequestParam("startDate") Long startDate,
            @RequestParam("endDate") Long endDate) {

        Integer count = Math.toIntExact(this.khachHangService.countKhachHangByDate(startDate, endDate));
        return ResponseEntity.ok(count);
    }
    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<ApiResponse> findCustomerByPhoneNumber(
            @PathVariable("phoneNumber")
            String phoneNumber) throws AppException {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get customer by phone number success")
                        .data(khachHangService.findCustomerByPhone(phoneNumber))
                        .build()
        );
    }

}
