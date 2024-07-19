package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.hoadon.SerialNumberDaBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SerialNumberDaBanRepository extends JpaRepository<SerialNumberDaBan, Long> {
    List<SerialNumberDaBan> findAllByHoaDonId(Long billId);
}
