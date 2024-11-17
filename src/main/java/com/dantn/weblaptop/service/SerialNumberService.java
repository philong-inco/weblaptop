package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.SerialNumberCreate;
import com.dantn.weblaptop.dto.request.update_request.SerialNumberUpdate;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.dto.response.SerialNumberResponse;
import com.dantn.weblaptop.entity.sanpham.SerialNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SerialNumberService {

    SerialNumberResponse add(SerialNumberCreate create);

    SerialNumberResponse update(SerialNumberUpdate update);

    boolean delete(Long id);

    List<SerialNumberResponse> getAll();

    Page<SerialNumberResponse> getAllPage(Pageable pageable);

    boolean existByAdd(String ma);

    boolean existByUpdate(String ma, Long id);

    SerialNumberResponse findById(Long id);

    SerialNumberResponse findByMa(String ma);

    void deleteAllByIdSPCT(Long idSPCT);

    void changeStatusToSeriNumberDaBan(Long idSerialNumber);


    List<SerialNumberResponse> findAllBySanPhamChiTietId(Long id);

    List<SerialNumberResponse> findAllBySanPhamChiTietIdActive(Long id);


    //mạnh
    List<SerialNumber> getSerialNumberByProductIdAndStatus(Long productId, Integer status);

    // page
    ResultPaginationResponse getAllSerialNumberByProductDetailIdAndStatus(
            Long productId, Integer status, Optional<String> page, Optional<String> size);


    ResultPaginationResponse getAllSerialNumberByProductDetailId(
            Long productId, Optional<String> page, Optional<String> size);

    ResultPaginationResponse findSerialNumbers(
            String maHoaDon,
            Long sanPhamChiTietId,
            String maSerial,
            Optional<String> page, Optional<String> size
    );

    ResultPaginationResponse findSerialNumbersByProductCode(
            String maHoaDon,
            String MaSanPhamChiTiet,
            String maSerial,
            Optional<String> page, Optional<String> size
    );

    ResultPaginationResponse getAllSerialNumberByProductDetailIdAndCodeSerial(
            Long productId, String codeSerial, Optional<String> page, Optional<String> size);


}
