package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.repository.HoaDonRepository;
import com.dantn.weblaptop.service.BanHangService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BanHangServiceImpl implements BanHangService {
    HoaDonRepository billRepository;
    @Override
    public List<String> getAllBillCode() {
        return billRepository.getAllByStatus();
    }
}
