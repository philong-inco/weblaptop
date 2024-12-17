package com.dantn.weblaptop.util.expiredBill;

import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.repository.HoaDonHinhThucThanhToanRepository;
import com.dantn.weblaptop.repository.HoaDonRepository;
import com.dantn.weblaptop.repository.LichSuHoaDonRepository;
import com.dantn.weblaptop.repository.SerialNumberDaBanRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
@EnableScheduling
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExpiredBillScheduler {
    HoaDonRepository hoaDonRepository;
    HoaDonHinhThucThanhToanRepository hoaDonHinhThucThanhToanRepository;
    LichSuHoaDonRepository lichSuHoaDonRepository;
    SerialNumberDaBanRepository serialNumberDaBanRepository;

    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "0 41 17 * * ?")
    public void scheduledFixedDelayTask() {
        System.out.println("scheduledFixedDelayTask");
        List<HoaDon> listBillClear = hoaDonRepository.listBillClear();
        for (HoaDon hoaDon : listBillClear) {
            System.out.println("scheduledFixedDelayTask 1");
            serialNumberDaBanRepository.deleteAllByHoaDonId(hoaDon.getId());
            lichSuHoaDonRepository.deleteAllByHoaDonId(hoaDon.getId());
            hoaDonHinhThucThanhToanRepository.deleteAllByHoaDonId(hoaDon.getId());

            hoaDonRepository.delete(hoaDon);
        }
    }
}
