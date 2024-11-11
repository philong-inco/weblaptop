package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Long>, JpaSpecificationExecutor<SanPhamChiTiet> {

    @Query("SELECT s FROM SanPhamChiTiet s")
    Page<SanPhamChiTiet> getAllPage(Pageable pageable);

    @Query("SELECT s FROM SanPhamChiTiet s")
    List<SanPhamChiTiet> getAllList();

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.trangThai = :status")
    Page<SanPhamChiTiet> findByStatusPage(@Param("status") Integer status, Pageable pageable);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.trangThai = :status")
    List<SanPhamChiTiet> findByStatusList(Integer status);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE LOWER(s.sanPham.ten) LIKE %:name%")
    Page<SanPhamChiTiet> findByProductNamePage(@Param("name") String productName, Pageable pageable);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE LOWER(s.sanPham.ten) LIKE %:name%")
    List<SanPhamChiTiet> findByProductNameList(@Param("name") String productName);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.sanPham.id = :id")
    Page<SanPhamChiTiet> findByProductIdPage(@Param("id") Long productId, Pageable pageable);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.sanPham.id = :id ORDER BY s.ngayTao DESC")
    List<SanPhamChiTiet> findByProductIdList(@Param("id") Long productId);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.sanPham.id = :id AND s.trangThai = 1")
    List<SanPhamChiTiet> findByProductIdListActive(@Param("id") Long productId);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE LOWER(s.ma) = :code")
    List<SanPhamChiTiet> isExistCode(@Param("code") String code);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.sanPham.id=:idSP " +
            "AND s.banPhim.id=:idBP AND s.cpu.id=:idCPU AND s.vga.id=:idVGA " +
            "AND s.heDieuHanh.id=:idHDT AND s.manHinh.id=:idMH AND s.ram.id=:idRAM " +
            "AND s.oCung.id=:idOC AND s.webcam.id=:idWC AND s.mauSac.id=:idMS")
    List<SanPhamChiTiet> isExistSanPhamChiTietForCreate(@Param("idSP") Long idSP,
                                                        @Param("idBP") Long idBP,
                                                        @Param("idCPU") Long idCPU,
                                                        @Param("idVGA") Long idVGA,
                                                        @Param("idHDT") Long idHDT,
                                                        @Param("idMH") Long idMH,
                                                        @Param("idRAM") Long idRAM,
                                                        @Param("idOC") Long idOC,
                                                        @Param("idWC") Long idWC,
                                                        @Param("idMS") Long idMS
    );

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.sanPham.id=:idSP " +
            "AND s.banPhim.id=:idBP AND s.cpu.id=:idCPU AND s.vga.id=:idVGA " +
            "AND s.heDieuHanh.id=:idHDT AND s.manHinh.id=:idMH AND s.ram.id=:idRAM " +
            "AND s.oCung.id=:idOC AND s.webcam.id=:idWC AND s.mauSac.id=:idMS AND s.id <> :idSPCT")
    List<SanPhamChiTiet> isExistSanPhamChiTietForUpdate(@Param("idSP") Long idSP,
                                                        @Param("idBP") Long idBP,
                                                        @Param("idCPU") Long idCPU,
                                                        @Param("idVGA") Long idVGA,
                                                        @Param("idHDT") Long idHDT,
                                                        @Param("idMH") Long idMH,
                                                        @Param("idRAM") Long idRAM,
                                                        @Param("idOC") Long idOC,
                                                        @Param("idWC") Long idWC,
                                                        @Param("idSPCT") Long id,
                                                        @Param("idMS") Long idMS
    );
}
