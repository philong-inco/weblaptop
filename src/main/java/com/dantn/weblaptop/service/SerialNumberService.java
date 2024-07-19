package com.dantn.weblaptop.service;

import com.dantn.weblaptop.entity.sanpham.SerialNumber;

import java.util.List;

public interface SerialNumberService {
    List<SerialNumber> getSerialNumberByProductIdAndStatus(Long productId, Integer status);
}
