package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.LichSuHoaDonRquest;
import com.dantn.weblaptop.dto.response.LichSuHoaDonResponse;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.repository.LichSuaHoaDonRepository;
import com.dantn.weblaptop.service.LichSuHoaDonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LichSuHoaDonServiceImpl implements LichSuHoaDonService {
    LichSuaHoaDonRepository lichSuaHoaDonRepository;

    @Override
    public LichSuHoaDonResponse create(LichSuHoaDonRquest request) {
        return null;
    }

    @Override
    public ResultPaginationResponse getBillHistoryByBillId(Long billId, Optional<String> page, Optional<String> size) {
        return null;
    }
}
