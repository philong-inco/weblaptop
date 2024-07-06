<<<<<<< HEAD:src/main/java/com/dantn/weblaptop/hoadon/service/HinhThucThanhToanService.java
package com.dantn.weblaptop.hoadon.service;

import com.dantn.weblaptop.entity.hoadon.HinhThucThanhToan;
import com.dantn.weblaptop.hoadon.dto.request.HinhThucThanhToanRequest;
import com.dantn.weblaptop.hoadon.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.hoadon.exception.AppException;
=======
package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.CreateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.entity.hoadon.HinhThucThanhToan;
import com.dantn.weblaptop.exception.AppException;
>>>>>>> main:src/main/java/com/dantn/weblaptop/service/HinhThucThanhToanService.java

import java.util.Optional;

public interface HinhThucThanhToanService {
    ResultPaginationResponse getPaymentMethodsPage(Optional<String> page, Optional<String> size);

<<<<<<< HEAD:src/main/java/com/dantn/weblaptop/hoadon/service/HinhThucThanhToanService.java
    HinhThucThanhToan createPaymentMethods(HinhThucThanhToanRequest request) ;

    HinhThucThanhToan updatePaymentMethods(Long id, HinhThucThanhToanRequest request) throws AppException;
=======
    HinhThucThanhToan createPaymentMethods(CreateHinhThucThanhToanRequest request) ;

    HinhThucThanhToan updatePaymentMethods(Long id, UpdateHinhThucThanhToanRequest request) throws AppException;
>>>>>>> main:src/main/java/com/dantn/weblaptop/service/HinhThucThanhToanService.java

    Boolean updateStatus(Long id);
}
