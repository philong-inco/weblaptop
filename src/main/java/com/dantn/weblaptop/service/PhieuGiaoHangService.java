package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.entity.hoadon.PhieuGiaoHang;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PhieuGiaoHangService {

    ResultPaginationResponse getAll(Specification<PhieuGiaoHang> specification , Pageable pageable);

}
