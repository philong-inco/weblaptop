package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.response.GioHangDetailResponse;
import com.dantn.weblaptop.entity.giohang.GioHangChiTiet;
import com.dantn.weblaptop.entity.sanpham.AnhSanPham;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
@Component
@RequiredArgsConstructor
public class GioHangChiTietMapper {

   private final   SanPhamChiTietMapper sanPhamChiTietMapper;
    public  GioHangDetailResponse toResponse(GioHangChiTiet entity) {
        Set<AnhSanPham> anhSanPhams = entity.getSanPhamChiTiet() != null
                ? entity.getSanPhamChiTiet().getAnhSanPhams()
                : null;
        String url = "";
        AnhSanPham firstImage = null;

        if (anhSanPhams != null && !anhSanPhams.isEmpty()) {
            firstImage = anhSanPhams.iterator().next();
            url = firstImage != null ? firstImage.getUrl() : "";
        }

        GioHangDetailResponse response = GioHangDetailResponse.builder()
                .idGioHangChiTiet(entity.getId())
                .idGioHang(entity.getGioHang() != null ? entity.getGioHang().getId() : null)
                .gia(entity.getGia())
                .soLuong(entity.getSoLuong())
                .trangThai(entity.getTrangThai())
                .idSanPhamSPCT(entity.getSanPhamChiTiet() != null ? entity.getSanPhamChiTiet().getId() : null)
                .idSanPham(entity.getSanPhamChiTiet()!=null ? entity.getSanPhamChiTiet().getSanPham().getId() : null)
                .maSPCT(entity.getSanPhamChiTiet() != null ? entity.getSanPhamChiTiet().getMa() : null)
                .tenSanPham(entity.getSanPhamChiTiet() != null ? entity.getSanPhamChiTiet().getSanPham().getTen() : null)
                .moTaSP(entity.getSanPhamChiTiet() != null ? entity.getSanPhamChiTiet().getSanPham().getMoTa() : null)
                .ram(entity.getSanPhamChiTiet() != null ? entity.getSanPhamChiTiet().getRam().getTen() : null)
                .cpu(entity.getSanPhamChiTiet() != null ? entity.getSanPhamChiTiet().getCpu().getTen() : null)
                .webcam(entity.getSanPhamChiTiet() != null ? entity.getSanPhamChiTiet().getWebcam().getTen() : null)
                .banPhim(entity.getSanPhamChiTiet() != null ? entity.getSanPhamChiTiet().getBanPhim().getTen() : null)
                .heDieuHanh(entity.getSanPhamChiTiet() != null ? entity.getSanPhamChiTiet().getHeDieuHanh().getTen() : null)
                .manHinh(entity.getSanPhamChiTiet() != null ? entity.getSanPhamChiTiet().getManHinh().getTen() : null)
                .mauSac(entity.getSanPhamChiTiet() != null ? entity.getSanPhamChiTiet().getMauSac().getTen() : null)
                .oCung(entity.getSanPhamChiTiet() != null ? entity.getSanPhamChiTiet().getOCung().getTen() : null)
                .vga(entity.getSanPhamChiTiet() != null ? entity.getSanPhamChiTiet().getVga().getTen() : null)
                .nhuCau(entity.getSanPhamChiTiet()!=null? entity.getSanPhamChiTiet().getSanPham().getNhuCau().getTen() : null)
                .thuongHieu(entity.getSanPhamChiTiet() !=null? entity.getSanPhamChiTiet().getSanPham().getThuongHieu().getTen() : null)
                .anh(url)
//                .sanPhamChiTietClientDTO(sanPhamChiTietMapper.entityToClient(entity.getSanPhamChiTiet()))
                .build();

        return response;
    }

}
