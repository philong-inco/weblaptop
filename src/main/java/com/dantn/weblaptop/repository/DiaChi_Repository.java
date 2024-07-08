package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.khachhang.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaChi_Repository extends JpaRepository<DiaChi, Integer> {
}
