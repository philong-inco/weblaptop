package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DotGiamGiaRepository extends JpaRepository<DotGiamGia, Long> {
    @Query("select d from DotGiamGia d where d.id != :idRequest and (d.ten = :tenRequest or d.ma = :maRequest)")
    DotGiamGia findByNameOrMaExcludingId(@Param("idRequest") Long idRequest, @Param("tenRequest") String tenRequest, @Param("maRequest") String maRequest);

    @Query("select  d from  DotGiamGia  d where d.ten =:tenRequest or d.ma =:maRequest")
    DotGiamGia findbyNameAndMa(@Param("tenRequest") String tenRequest, @Param("maRequest") String maRequest);

    @Query("select d from DotGiamGia d")
    Page<DotGiamGia> finAllDotGiamGia(Pageable pageable);
}
