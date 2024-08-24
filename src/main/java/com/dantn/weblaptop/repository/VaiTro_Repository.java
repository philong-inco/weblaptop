package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface VaiTro_Repository extends JpaRepository<VaiTro, Integer> {
    @Query(value = "SELECT v FROM VaiTro v")
    Page<VaiTro> pageAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE VaiTro v SET v.trangThai = :trangThai WHERE v.id = :id")
    void revertStatus(@Param("trangThai") Integer trangThai, @Param("id") Long id);

    @Query(value = "SELECT v FROM VaiTro v WHERE v.id = :id")
    Optional<VaiTro> findById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "SELECT v FROM VaiTro v WHERE v.ten like :name")
    boolean existsByName(@Param("name") String name);

    @Query(value = "SELECT vt FROM NhanVienVaiTro nvvt JOIN VaiTro vt ON nvvt.vaiTro.id = vt.id WHERE nvvt.id = :id")
    VaiTro findVaiTroByIdNhanVienVaiTro(@Param("id") Long id);

    @Query(value = "SELECT vt FROM VaiTro vt WHERE vt.ten in :ten")
    Set<VaiTro> findVaiTroByTen(@Param("ten") Set<String> ten);
}
