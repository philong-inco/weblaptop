package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.response.SerialNumberResponse;
import com.dantn.weblaptop.entity.sanpham.SerialNumber;
import com.dantn.weblaptop.mapper.impl.SerialNumberMapper;
import com.dantn.weblaptop.repository.SerialNumberRepository;
import com.dantn.weblaptop.service.SerialNumberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SerialNumberServiceImpl  implements SerialNumberService {
    SerialNumberRepository serialNumberRepository;
    // note : 0 là chưa bán
    @Override
    public List<SerialNumber> getSerialNumberByProductIdAndStatus(Long productId, Integer status) {
        return serialNumberRepository.findBySanPhamChiTietIdAndTrangThai(productId, status);
    }
}
