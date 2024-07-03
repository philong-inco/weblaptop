package com.dantn.weblaptop.hoadon.fake;

import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhieuGiamGiaRepositoryFAKE extends JpaRepository<PhieuGiamGia, Long> {

    Optional<PhieuGiamGia> findByMa(String code);

}
