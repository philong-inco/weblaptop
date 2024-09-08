package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.sanpham.AnhSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnhSanPhamChiTietRepository extends JpaRepository<AnhSanPham, Long> {

    @Query("SELECT a FROM AnhSanPham a WHERE a.sanPhamChiTiet.id = :id")
    List<AnhSanPham> findByIdSPCT(@Param("id") Long idSPCT);

    @Query("DELETE FROM AnhSanPham a WHERE a.sanPhamChiTiet.id = :idSPCT")
    void deleteAllByIdSPCT(@Param("idSPCT") Long idSPCT);
}
