package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.sanpham.SerialNumber;
<<<<<<< HEAD
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
=======
import org.springframework.data.jpa.repository.JpaRepository;
>>>>>>> manhntph37150
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SerialNumberRepository extends JpaRepository<SerialNumber, Long> {
<<<<<<< HEAD

    @Query("SELECT s FROM SerialNumber s")
    List<SerialNumber> getAllList();

    @Query("SELECT s FROM SerialNumber s")
    Page<SerialNumber> getAllPage(Pageable pageable);

    @Query("SELECT s FROM SerialNumber s WHERE lower(s.ma) = :ma")
    List<SerialNumber> existByMaForAdd(@Param("ma") String ma);
    @Query("SELECT s FROM SerialNumber s WHERE lower(s.ma) = :ma AND s.id <> :id")
    List<SerialNumber> existByMaForUpdate(@Param("ma") String ma, @Param("id") Long id);

    SerialNumber findByMa(String ma);
=======
    List<SerialNumber> findBySanPhamChiTietIdAndTrangThai(Long productDetailId, Integer status);

    List<SerialNumber> findAllBySanPhamChiTietId(Long productDetailId);
>>>>>>> manhntph37150
}
