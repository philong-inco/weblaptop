package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.FindSanPhamFilterByName;
import com.dantn.weblaptop.dto.request.create_request.NhuCauCreate;
import com.dantn.weblaptop.dto.request.create_request.SanPhamCreate;
import com.dantn.weblaptop.dto.request.create_request.ThuongHieuCreate;
import com.dantn.weblaptop.dto.request.update_request.NhuCauUpdate;
import com.dantn.weblaptop.dto.request.update_request.SanPhamUpdate;
import com.dantn.weblaptop.dto.request.update_request.ThuongHieuUpdate;
import com.dantn.weblaptop.dto.response.NhuCauResponse;
import com.dantn.weblaptop.dto.response.SanPhamResponse;
import com.dantn.weblaptop.dto.response.ThuongHieuResponse;
import com.dantn.weblaptop.entity.sanpham.NhuCau;
import com.dantn.weblaptop.entity.sanpham.SanPham;
import com.dantn.weblaptop.entity.sanpham.ThuongHieu;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.BanPhim;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.CPU;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.HeDieuHanh;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.ManHinh;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.MauSac;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.OCung;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.RAM;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.VGA;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.Webcam;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.generics.IGenericsMapper;
import com.dantn.weblaptop.generics.IGenericsRepository;
import com.dantn.weblaptop.repository.SanPhamRepository;
import com.dantn.weblaptop.util.GenerateCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamService extends GenericsService<SanPham, Long, SanPhamCreate, SanPhamUpdate, SanPhamResponse> {

    private final GenericsService<ThuongHieu, Long, ThuongHieuCreate, ThuongHieuUpdate, ThuongHieuResponse> serviceThuongHieu;
    private final GenericsService<NhuCau, Long, NhuCauCreate, NhuCauUpdate, NhuCauResponse> serviceNhuCau;

    private final SanPhamRepository sanPhamRepository;

    public SanPhamService(IGenericsRepository<SanPham, Long> genericsRepository,
                          IGenericsMapper<SanPham, SanPhamCreate, SanPhamUpdate, SanPhamResponse> genericsMapper,
                          GenericsService<ThuongHieu, Long, ThuongHieuCreate, ThuongHieuUpdate, ThuongHieuResponse> serviceThuongHieu,
                          GenericsService<NhuCau, Long, NhuCauCreate, NhuCauUpdate, NhuCauResponse> serviceNhuCau,
                          SanPhamRepository sanPhamRepository) {
        super(genericsRepository, genericsMapper);
        this.serviceThuongHieu = serviceThuongHieu;
        this.serviceNhuCau = serviceNhuCau;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public void convertCreateToEntity(SanPhamCreate create, SanPham entity) {
        String ma;
        do {
            ma = GenerateCode.generateCode(GenerateCode.VGA_PREFIX);
        } while (existByCode(ma));
        ThuongHieu thuongHieu = serviceThuongHieu.findEntityById(create.getThuongHieuId());
        NhuCau nhuCau = serviceNhuCau.findEntityById(create.getNhuCauId());
        entity.setMa(ma);
        entity.setThuongHieu(thuongHieu);
        entity.setNhuCau(nhuCau);
    }

    @Override
    public void convertUpdateToEntity(SanPhamUpdate update, SanPham entity) {
        ThuongHieu thuongHieu = serviceThuongHieu.findEntityById(update.getThuongHieuId());
        NhuCau nhuCau = serviceNhuCau.findEntityById(update.getNhuCauId());
        entity.setThuongHieu(thuongHieu);
        entity.setNhuCau(nhuCau);
    }

    @Override
    public void beforeAdd(SanPhamCreate create) {
        if (genericsRepository.isExistName(create.getTen().trim()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }

    @Override
    public void beforeUpdate(SanPhamUpdate update) {
        if (genericsRepository.isExistNameAndDifferentId(update.getTen().trim(), update.getId()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }

    public Page<SanPhamResponse> findWithFilterByName(FindSanPhamFilterByName attribute, Pageable pageable) {
        Specification<SanPham> spec = Specification
                .where(SanPhamSpecifications.hasTen(attribute.getTenSanPham()))
                .and(SanPhamSpecifications.hasMa(attribute.getMa()))
                .and(SanPhamSpecifications.hasTrangThai(attribute.getTrangThai()))
                .and(SanPhamSpecifications.ngayTaoTruoc(attribute.getNgayTaoTruoc()))
                .and(SanPhamSpecifications.ngayTaoSau(attribute.getNgayTaoSau()))
                .and(SanPhamSpecifications.ngaySuaTruoc(attribute.getNgaySuaTruoc()))
                .and(SanPhamSpecifications.ngaySuaSau(attribute.getNgaySuaSau()))
                // điều kiện các bảng cha của sản phẩm
                .and(SanPhamSpecifications.hasNhuCau(attribute.getTenNhuCau()))
                .and(SanPhamSpecifications.hasThuongHieu(attribute.getTenThuongHieu()))
                // điều kiện các bảng thuộc tính sản phẩm chi tiết
                .and(SanPhamSpecifications.hasNameAttribute(RAM.class, "ram", attribute.getTenRam()))
                .and(SanPhamSpecifications.hasNameAttribute(CPU.class, "cpu", attribute.getTenCPU()))
                .and(SanPhamSpecifications.hasNameAttribute(VGA.class, "vga", attribute.getTenVGA()))
                .and(SanPhamSpecifications.hasNameAttribute(ManHinh.class, "manHinh", attribute.getTenManHinh()))
                .and(SanPhamSpecifications.hasNameAttribute(BanPhim.class, "banPhim", attribute.getTenBanPhim()))
                .and(SanPhamSpecifications.hasNameAttribute(MauSac.class, "mauSac", attribute.getTenMau()))
                .and(SanPhamSpecifications.hasNameAttribute(Webcam.class, "webcam", attribute.getTenWebcam()))
                .and(SanPhamSpecifications.hasNameAttribute(HeDieuHanh.class, "heDieuHanh", attribute.getTenHeDieuHanh()))
                .and(SanPhamSpecifications.hasNameAttribute(OCung.class, "oCung", attribute.getTenOCung()))
                .and(SanPhamSpecifications.hasGiaNhoHon(attribute.getGiaNhoHon()))
                .and(SanPhamSpecifications.hasGiaLonHon(attribute.getGiaLonHon()));

        return genericsMapper.pageEntityToPageResponse(sanPhamRepository.findAll(spec, pageable));
    }

    public Page<SanPhamResponse> findWithFilterById(FindSanPhamFilterByName attribute, Pageable pageable) {
        Specification<SanPham> spec = Specification
                .where(SanPhamSpecifications.hasTen(attribute.getTenSanPham()))
                .and(SanPhamSpecifications.hasMa(attribute.getMa()))
                .and(SanPhamSpecifications.hasTrangThai(attribute.getTrangThai()))
                .and(SanPhamSpecifications.ngayTaoTruoc(attribute.getNgayTaoTruoc()))
                .and(SanPhamSpecifications.ngayTaoSau(attribute.getNgayTaoSau()))
                .and(SanPhamSpecifications.ngaySuaTruoc(attribute.getNgaySuaTruoc()))
                .and(SanPhamSpecifications.ngaySuaSau(attribute.getNgaySuaSau()))
                // điều kiện các bảng cha của sản phẩm
                .and(SanPhamSpecifications.hasNhuCauId(attribute.getTenNhuCau()))
                .and(SanPhamSpecifications.hasThuongHieuId(attribute.getTenThuongHieu()))
                // điều kiện các bảng thuộc tính sản phẩm chi tiết
                .and(SanPhamSpecifications.hasIdAttribute(RAM.class, "ram", attribute.getTenRam()))
                .and(SanPhamSpecifications.hasIdAttribute(CPU.class, "cpu", attribute.getTenCPU()))
                .and(SanPhamSpecifications.hasIdAttribute(VGA.class, "vga", attribute.getTenVGA()))
                .and(SanPhamSpecifications.hasIdAttribute(ManHinh.class, "manHinh", attribute.getTenManHinh()))
                .and(SanPhamSpecifications.hasIdAttribute(BanPhim.class, "banPhim", attribute.getTenBanPhim()))
                .and(SanPhamSpecifications.hasIdAttribute(MauSac.class, "mauSac", attribute.getTenMau()))
                .and(SanPhamSpecifications.hasIdAttribute(Webcam.class, "webcam", attribute.getTenWebcam()))
                .and(SanPhamSpecifications.hasIdAttribute(HeDieuHanh.class, "heDieuHanh", attribute.getTenHeDieuHanh()))
                .and(SanPhamSpecifications.hasIdAttribute(OCung.class, "oCung", attribute.getTenOCung()))
                .and(SanPhamSpecifications.hasGiaNhoHon(attribute.getGiaNhoHon()))
                .and(SanPhamSpecifications.hasGiaLonHon(attribute.getGiaLonHon()));

        return genericsMapper.pageEntityToPageResponse(sanPhamRepository.findAll(spec, pageable));
    }
}
