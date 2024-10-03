package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.SerialNumberCreate;
import com.dantn.weblaptop.dto.request.update_request.SerialNumberUpdate;
import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.dto.response.ResponseLong;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.dto.response.SerialNumberResponse;
import com.dantn.weblaptop.service.SanPhamChiTietService;
import com.dantn.weblaptop.service.SerialNumberService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                ResponseEntity.status(HttpStatus.OK).body(new ResponseLong<>(
                        999, "Existed!", true, null, null, null, null
                ))
                :
                ResponseEntity.status(HttpStatus.OK).body(new ResponseLong<>(
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

    @GetMapping("/product-detail/{productId}")
    public ApiResponse<ResultPaginationResponse> getSerialNumberByProduct
    (@PathVariable("productId") Long productId,
     @RequestParam(name = "status", defaultValue = "0") Integer status,
     @RequestParam(name = "page", defaultValue = "0") Optional<String> page,
     @RequestParam(name = "size", defaultValue = "5") Optional<String> size
    ) {
        return ApiResponse.<ResultPaginationResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get SerialNumber By Product Success")
                .data(serialNumberService.getAllSerialNumberByProductDetailIdAndStatus(productId, status, page, size))
                .build();
    }


    @GetMapping("find-by-spct-id/{id}")
    public ResponseEntity<ResponseLong<List<SerialNumberResponse>>> findAllBySPCTId(@PathVariable("id")Long id)
    {
        List<SerialNumberResponse> result = serialNumberService.findAllBySanPhamChiTietId(id);
        List<SerialNumberResponse> sortedResult = result.stream()
                .sorted(Comparator.comparing(SerialNumberResponse::getTrangThai))
                .collect(Collectors.toList());
        if (result != null || result.size() > 0)
            return ResponseEntity.ok().body(new ResponseLong<>(
                    200, "Get successfully", sortedResult
            ));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseLong<>(
                404, "Not have serials", null
        ));
    }

    @GetMapping("find-by-spct-id-active/{id}")
    public ResponseEntity<ResponseLong<List<SerialNumberResponse>>> findAllBySPCTIdAcitve(@PathVariable("id")Long id)
    {
        List<SerialNumberResponse> result = serialNumberService.findAllBySanPhamChiTietIdActive(id);
        if (result != null || result.size() > 0)
            return ResponseEntity.ok().body(new ResponseLong<>(
                    200, "Get successfully", result
            ));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseLong<>(
                404, "Not have serials", null
        ));
    }


    @GetMapping("find-by-ma/{ma}")
    public ResponseEntity<ResponseLong<SerialNumberResponse>> findByMa(@PathVariable("ma")String ma)
    {
        SerialNumberResponse result = serialNumberService.findByMa(ma.trim());
        if (result != null)
            return ResponseEntity.ok().body(new ResponseLong<>(
                    200, "Get successfully", result
            ));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseLong<>(
                404, "Not have serials", null
        ));
    }

    @GetMapping("change-status-to-serial-da-ban/{id}")
    public ResponseEntity<ResponseLong<String>> changeStatusSerialToDaBan(@PathVariable("id")Long id){
        serialNumberService.changeStatusToSeriNumberDaBan(id);
        return ResponseEntity.ok().body(new ResponseLong<>(
                200, "Change succesfully",null
        ));
    }
}
