package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.SerialNumberCreate;
import com.dantn.weblaptop.dto.request.update_request.SerialNumberUpdate;
import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.dto.response.ResponseLong;
import com.dantn.weblaptop.dto.response.SerialNumberResponse;
import com.dantn.weblaptop.entity.sanpham.SanPham;
import com.dantn.weblaptop.repository.SerialNumberRepository;
import com.dantn.weblaptop.service.SanPhamChiTietService;
import com.dantn.weblaptop.service.SerialNumberService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.IntegerRange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/serial-number")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SerialNumberController {

    SerialNumberService serialNumberService;
    SanPhamChiTietService sanPhamChiTietService;

    @GetMapping("all")
    public ResponseEntity<ResponseLong<List<SerialNumberResponse>>> getAllList() {
        List<SerialNumberResponse> list = serialNumberService.getAll();
        if (list.size() > 0)
            return ResponseEntity.ok().body(new ResponseLong<>(
                    200, "GET succesfully", list, null, null, null, null
            ));
        return ResponseEntity.ok().body(new ResponseLong<>(
                999, "Is Empty", null, null, null, null, null
        ));
    }

    @GetMapping("page")
    public ResponseEntity<ResponseLong<List<SerialNumberResponse>>> getAllPage(@RequestParam(value = "page", required = false, defaultValue = "0") String page,
                                                                               @RequestParam(value = "size", required = false, defaultValue = "5") String size) {
        Pageable pageable;
        try {
            pageable = PageRequest.of(Integer.valueOf(page), Integer.valueOf(size));
        } catch (Exception e) {
            pageable = PageRequest.of(0, 5);
        }
        Page<SerialNumberResponse> pageResult = serialNumberService.getAllPage(pageable);
        if (pageResult.getContent().size() > 0) {
            ResponseEntity.ok().body(new ResponseLong<>(
                    200,
                    "GET successfully",
                    pageResult.getContent(),
                    String.valueOf(pageResult.getNumber()),
                    String.valueOf(pageResult.getSize()),
                    String.valueOf(pageResult.getTotalPages()),
                    String.valueOf(pageResult.getTotalElements())
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseLong<>(
                999, "Is Empty", null, null, null, null, null
        ));
    }

    @PostMapping("add")
    public ResponseEntity<ResponseLong<SerialNumberResponse>> add(@RequestBody @Valid SerialNumberCreate create) {
        if (sanPhamChiTietService.findById(create.getSanPhamChiTietId()) == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseLong<>(
                    999, "ID SPCT not found", null, null, null, null, null
            ));
        if (serialNumberService.existByAdd(create.getMa()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseLong<>(
                    999, "Ma is exist", null, null, null, null, null
            ));
        return ResponseEntity.ok().body(new ResponseLong<>(
                200, "Add successfully", serialNumberService.add(create), null, null, null, null
        ));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ResponseLong<SerialNumberResponse>> update(@RequestBody @Valid SerialNumberUpdate update,
                                                                     @PathVariable("id") String id) {
        try {
            if (Long.valueOf(id) != update.getId())
                throw new Exception();

            if (sanPhamChiTietService.findById(update.getSanPhamChiTietId()) == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseLong<>(
                        999, "ID SPCT not found", null, null, null, null, null
                ));
            if (serialNumberService.existByUpdate(update.getMa(), update.getId()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseLong<>(
                        999, "Ma is exist", null, null, null, null, null
                ));
            return ResponseEntity.ok().body(new ResponseLong<>(
                    200, "Update successfully", serialNumberService.update(update), null, null, null, null
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseLong<>(
                    999, "Id invalid", null, null, null, null, null
            ));
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseLong<String>> delete(@PathVariable("id") String id) {
        try {
            if (Long.valueOf(id) <= 0)
                throw new Exception();
            if (serialNumberService.findById(Long.valueOf(id)) == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseLong<>(
                        999, "Not found with id", null, null, null, null, null
                ));
            serialNumberService.delete(Long.valueOf(id));
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseLong<>(
                    200, "Delete succesfully", null, null, null, null, null
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseLong<>(
                    999, "Id invalid", null, null, null, null, null
            ));
        }
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<ResponseLong<SerialNumberResponse>> detail(@PathVariable("id") String idStr) {
        Long id = null;
        try {
            id = Long.valueOf(idStr);
            SerialNumberResponse response = serialNumberService.findById(id);
            if (response == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseLong<>(
                        999, "Not found", null, null, null, null, null
                ));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseLong<>(
                    200, "Find succesfully", response, null, null, null, null
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseLong<>(
                    999, "Id invalid", null, null, null, null, null
            ));
        }
    }

    @GetMapping("exist-for-add")
    public ResponseEntity<ResponseLong<Boolean>> existForAdd(@RequestParam("ma") String ma) {
        boolean check = serialNumberService.existByAdd(ma);
        return (check) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseLong<>(
                        999, "Existed!", true, null, null, null, null
                ))
                :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseLong<>(
                        200, "Valid", false, null, null, null, null
                ));
    }

    @GetMapping("exist-for-update")
    public ResponseEntity<ResponseLong<Boolean>> existForUpdate(@RequestParam("ma") String ma, @RequestParam("id") String idStr) {
        Long id = null;
        try {
            id = Long.valueOf(idStr.trim());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseLong<>(
                    999, "Id invalid!", true, null, null, null, null
            ));
        }
        boolean check = serialNumberService.existByUpdate(ma, id);
        return (check) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseLong<>(
                        999, "Existed!", true, null, null, null, null
                ))
                :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseLong<>(
                        200, "Valid", false, null, null, null, null
                ));
    }

//    mạnh
    @GetMapping("serial-number/product-detail/{productDetailId}")
    public ResponseEntity<ApiResponse> getSerialNumberByProductDetailId(
            @PathVariable(name = "productDetailId") Long productDetailId
    ){
        return ResponseEntity.ok(ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Danh sach serial với idSPCT")
//                .data(serialNumberService.getAllSerialNumberByProductDetailIdAndStatus())
                .build());

    }
}
