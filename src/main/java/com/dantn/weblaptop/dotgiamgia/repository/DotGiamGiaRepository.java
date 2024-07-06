package com.dantn.weblaptop.dotgiamgia.repository;

import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DotGiamGiaRepository extends JpaRepository<DotGiamGia,Long> {
}
