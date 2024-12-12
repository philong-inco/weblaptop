package com.dantn.weblaptop.mapper.impl;


import com.dantn.weblaptop.dto.response.AnhSanPhamResponse;
import com.dantn.weblaptop.dto.response.SPCTForGemini;
import com.dantn.weblaptop.dto.response.SanPhamChiTietClientDTO;
import com.dantn.weblaptop.dto.response.SerialNumberResponse;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;

import com.dantn.weblaptop.entity.sanpham.SanPham;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import com.dantn.weblaptop.entity.sanpham.SerialNumber;
import com.dantn.weblaptop.generics.GenericsMapper;
import com.dantn.weblaptop.dto.request.create_request.SanPhamChiTietCreate;
import com.dantn.weblaptop.dto.request.update_request.SanPhamChiTietUpdate;
import com.dantn.weblaptop.dto.response.SanPhamChiTietResponse;
import com.dantn.weblaptop.repository.SerialNumberRepository;
import com.dantn.weblaptop.service.AnhSanPhamService;
import com.dantn.weblaptop.service.DotGiamGiaService;
import com.dantn.weblaptop.service.SerialNumberService;
import com.dantn.weblaptop.util.ConvertStringToArray;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SanPhamChiTietMapper extends GenericsMapper<SanPhamChiTiet, SanPhamChiTietCreate, SanPhamChiTietUpdate, SanPhamChiTietResponse> {


    SerialNumberService serialNumberService;
    AnhSanPhamService anhSanPhamService;
    DotGiamGiaService dotGiamGiaService;


    @Override
    public SanPhamChiTiet createToEntity(SanPhamChiTietCreate create) {
        SanPhamChiTiet spct = SanPhamChiTiet.builder()
                .giaBan(new BigDecimal(create.getGiaBan()))
                .trangThai(create.getTrangThai())
                .build();
        return spct;
    }

    @Override
    public SanPhamChiTiet updateToEntity(SanPhamChiTietUpdate update, SanPhamChiTiet entity) {
        entity.setGiaBan(new BigDecimal(update.getGiaBan()));
        entity.setTrangThai(update.getTrangThai());
        return entity;
    }

    @Override
    public SanPhamChiTietResponse entityToResponse(SanPhamChiTiet entity) {
        SanPhamChiTietResponse response = SanPhamChiTietResponse.builder()
                .id(entity.getId())
                .ma(entity.getMa())
                .giaBan(entity.getGiaBan() + "")
                .trangThai(entity.getTrangThai())
                .sanPham(entity.getSanPham().getTen())
                .maSanPham(entity.getSanPham().getMa())
                .sanPhamId(entity.getSanPham().getId()+"")
                .ram(entity.getRam().getTen())
                .idRam(entity.getRam().getId())
                .cpu(entity.getCpu().getTen())
                .idCPU(entity.getCpu().getId())
                .webcam(entity.getWebcam().getTen())
                .idWebcam(entity.getWebcam().getId())
                .banPhim(entity.getBanPhim().getTen())
                .idBanPhim(entity.getBanPhim().getId())
                .heDieuHanh(entity.getHeDieuHanh().getTen())
                .idHeDieuHanh(entity.getHeDieuHanh().getId())
                .manHinh(entity.getManHinh().getTen())
                .idManHinh(entity.getManHinh().getId())
                .mauSac(entity.getMauSac().getTen())
                .idMauSac(entity.getMauSac().getId())
                .oCung(entity.getOCung().getTen())
                .idOCung(entity.getOCung().getId())
                .vga(entity.getVga().getTen())
                .idVGA(entity.getVga().getId())
                .ngayTao(entity.getNgayTao() + "")
                .ngaySua(entity.getNgaySua() + "")
                .nguoiTao(entity.getNguoiTao())
                .nguoiSua(entity.getNguoiSua())
                .build();
        response.convertTime();
        List<SerialNumberResponse> listSeri = serialNumberService.findAllBySanPhamChiTietIdActive(entity.getId());
        List<AnhSanPhamResponse> listAnh = anhSanPhamService.getAllBySPCTId(entity.getId());
        String listSeriStr = "";
        String listAnhStr = "";
        if (listSeri != null && listSeri.size() > 0)
            listSeriStr = ConvertStringToArray.setSeriNumberToNameString(listSeri);
        if (listAnh != null && listAnh.size() > 0)
            listAnhStr = ConvertStringToArray.setAnhSanPhamToNameString(listAnh);
        response.setListSerialNumber(listSeriStr);
        response.setListUrlAnhSanPham(listAnhStr);

        return response;
    }

    @Override
    public List<SanPhamChiTietResponse> listEntityToListResponse(List<SanPhamChiTiet> entityList) {
        entityList.sort(Comparator.comparingLong(SanPhamChiTiet::getNgayTao).reversed());
        return entityList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<SanPhamChiTietResponse> pageEntityToPageResponse(Page<SanPhamChiTiet> entityPage) {
        List<SanPhamChiTietResponse> list = entityPage.getContent().stream()
                .map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }

    public SanPhamChiTietClientDTO entityToClient(SanPhamChiTiet entity){
        SanPhamChiTietClientDTO client = SanPhamChiTietClientDTO.builder()
                .id(entity.getId())
                .maSPCT(entity.getMa())
                .sanPhamId(entity.getSanPham().getId())
                .maSP(entity.getSanPham().getMa())
                .tenSanPham(entity.getSanPham().getTen())
                .moTaSP(entity.getSanPham().getMoTa())
                .idNhuCau(entity.getSanPham().getNhuCau().getId())
                .nhuCau(entity.getSanPham().getNhuCau().getTen())
                .idThuongHieu(entity.getSanPham().getThuongHieu().getId())
                .thuongHieu(entity.getSanPham().getThuongHieu().getTen())
                .giaBan(entity.getGiaBan()+"")
                .idBanPhim(entity.getBanPhim().getId())
                .banPhim(entity.getBanPhim().getTen())
                .idCPU(entity.getCpu().getId())
                .cpu(entity.getCpu().getTen())
                .idHeDieuHanh(entity.getHeDieuHanh().getId())
                .heDieuHanh(entity.getHeDieuHanh().getTen())
                .idManHinh(entity.getManHinh().getId())
                .manHinh(entity.getManHinh().getTen())
                .idMauSac(entity.getMauSac().getId())
                .mauSac(entity.getMauSac().getTen())
                .idOcung(entity.getOCung().getId())
                .oCung(entity.getOCung().getTen())
                .idRam(entity.getRam().getId())
                .ram(entity.getRam().getTen())
                .idVGA(entity.getVga().getId())
                .vga(entity.getVga().getTen())
                .idWebcam(entity.getWebcam().getId())
                .webcam(entity.getWebcam().getTen())
                .trangThai(entity.getTrangThai())
                .build();

        // check cac gia tri con lai
        // check anh
        List<AnhSanPhamResponse> listAnh = anhSanPhamService.getAllBySPCTId(entity.getId());
        listAnh = (listAnh != null && listAnh.size() > 3) ? listAnh.subList(0, 3) : listAnh;
        client.setListUrlAnhSanPham(ConvertStringToArray.setAnhSanPhamToNameString(listAnh));
        // tạm
//        client.setListUrlAnhSanPham("https://macstores.vn/wp-content/uploads/2023/04/thinkpad_x1_carbon_gen_11_1.png,https://thegioiso365.vn/wp-content/uploads/2022/10/x1-gen-10-13-768x766.jpg,https://thegioiso365.vn/wp-content/uploads/2022/10/17-1-1536x864.jpg");

        // check seri count -  tonkho
        List<SerialNumberResponse> listSeri = serialNumberService.findAllBySanPhamChiTietIdActive(entity.getId());
        client.setTonKhoConLai(listSeri.size()+"");

        // check khuyen mai
        // dùng stream gọi hàm tính giảm giá


        List<DotGiamGia> listDotGiamGia = dotGiamGiaService.getDotGiamGiaBySPCTId(entity.getId());
        Optional<DotGiamGia> bestPromo = listDotGiamGia.stream()
                .max((promo1, promo2) -> {
                    BigDecimal value1 = promo1.getTienGiamGia(entity.getGiaBan());
                    BigDecimal value2 = promo2.getTienGiamGia(entity.getGiaBan());
                    return value1.compareTo(value2);
                });
        if (bestPromo.isPresent()){
            DotGiamGia dotGiamGia = bestPromo.get();
            client.setSoTienDuocGiam(dotGiamGia.getTienGiamGia(entity.getGiaBan()) +"");
            client.setGiaSauKhuyenMai(dotGiamGia.getTienSauKhiGiam(entity.getGiaBan()) +"");
            client.setIdKhuyenMai(dotGiamGia.getId());
            client.setMaKhuyenMai(dotGiamGia.getMa());
            if (dotGiamGia.getLoaiChietKhau() == 1){
                client.setTenKhuyenMai("Giảm " + dotGiamGia.getGiaTriGiam() + "%");
            } else if (dotGiamGia.getLoaiChietKhau() == 2){
                client.setTenKhuyenMai("Giảm " + dotGiamGia.getGiaTriGiam() + "đ");
            }

        }
        return client;
    }

    public List<SanPhamChiTietClientDTO> listEntityToClient(List<SanPhamChiTiet> list){
        return list.stream().map(this::entityToClient).collect(Collectors.toList());
    }

    public List<SPCTForGemini> listEntityToClientGemini(List<SanPhamChiTiet> list){
        List<SanPhamChiTietClientDTO> clientDto = listEntityToClient(list);
        List<SPCTForGemini> result = clientDto.stream().filter(x -> Integer.valueOf(x.getTonKhoConLai()) > 0).map(x -> SPCTForGemini.toResult(x)).collect(Collectors.toList());
        return result;
    }

    public Page<SanPhamChiTietClientDTO> pageEntityToClient(Page<SanPhamChiTiet> entityPage) {
        List<SanPhamChiTietClientDTO> list = entityPage.getContent().stream()
                .map(this::entityToClient).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
