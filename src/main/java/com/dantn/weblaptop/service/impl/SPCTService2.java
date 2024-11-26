package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import com.dantn.weblaptop.entity.sanpham.SerialNumber;
import com.dantn.weblaptop.repository.SanPhamChiTietRepository;
import com.dantn.weblaptop.repository.SerialNumberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SPCTService2 {
    SerialNumberRepository serialRepo;
    SanPhamChiTietRepository spctRepository;


    public void tatTrangThaiTheoIdSP(Long idSP) {
        List<SanPhamChiTiet> spcts = spctRepository.findByProductIdListActive(idSP);
        for (SanPhamChiTiet s : spcts) {
            s.setTrangThai(0);
            spctRepository.save(s);
        }
    }


    public void batTrangThaiTheoIdSP(Long idSP) {
        List<SanPhamChiTiet> spcts = spctRepository.findByProductIdListInActive(idSP);
        for (SanPhamChiTiet s : spcts) {
            List<SerialNumber> listSeri = serialRepo.findBySanPhamChiTietIdActive(s.getId());
            if(listSeri.size()>0){
                s.setTrangThai(1);
                spctRepository.save(s);
            }
        }
    }
}
