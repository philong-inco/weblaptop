package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.hoadon.SerialNumberDaBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerialNumberDaBanRepository extends JpaRepository<SerialNumberDaBan, Long> {
}
