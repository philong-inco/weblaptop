package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.CreateSerialNumberDaBanRequest;
import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.service.SerialNumberDaBanService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/serial-number-sold")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SerialNumberDaBanController {
    SerialNumberDaBanService serialNumberDaBanService;

    @GetMapping
    public ResponseEntity<ApiResponse> getSerialNumberSold(@RequestParam(name = "code") String code){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setData(serialNumberDaBanService.getSerialNumberDaBanPage(code));
        apiResponse.setMessage("Lấy danh sách sản phẩm đã có trong hóa đơn : "+ code);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CreateSerialNumberDaBanRequest request) throws AppException {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.CREATED.value());
        apiResponse.setData(serialNumberDaBanService.create(request));
        apiResponse.setMessage("Thêm mới thành công 1 sản phẩm");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable("id") Long id
    ){
        serialNumberDaBanService.delete(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setData(null);
        apiResponse.setMessage("Hủy thành công ");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
