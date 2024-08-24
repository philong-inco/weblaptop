package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.FindSanPhamChiTietByFilter;
import com.dantn.weblaptop.dto.request.create_request.SanPhamChiTietCreate;
import com.dantn.weblaptop.dto.request.update_request.SanPhamChiTietUpdate;
import com.dantn.weblaptop.dto.response.SanPhamChiTietResponse;
import com.dantn.weblaptop.entity.sanpham.SanPham;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.BanPhim;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.CPU;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.HeDieuHanh;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.ManHinh;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.MauSac;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.OCung;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.RAM;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.VGA;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.Webcam;
import com.dantn.weblaptop.mapper.impl.SanPhamChiTietMapper;
import com.dantn.weblaptop.repository.SanPhamChiTietRepository;
import com.dantn.weblaptop.service.SanPhamChiTietService;
import com.dantn.weblaptop.util.ConvertStringToArray;
import com.dantn.weblaptop.util.GenerateCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SanPhamChiTietServiceImpl implements SanPhamChiTietService {

    SanPhamChiTietMapper spctMapper;
    SanPhamChiTietRepository spctRepository;
    RAMService ramService;
    CPUService cpuService;
    VGAService vgaService;
    ManHinhService manHinhService;
    BanPhimService banPhimService;
    OCungService oCungService;
    HeDieuHanhService heDieuHanhService;
    WebcamService webcamService;
    MauSacService mauSacService;
    SanPhamService sanPhamService;

    SanPhamChiTietSpecificationInner specificationInner;
    SanPhamChiTietSpecificationJoin specificationJoin;

    @Override
    public List<SanPhamChiTietResponse> getAllList() {
        return spctMapper.listEntityToListResponse(spctRepository.getAllList());
    }

    @Override
    public Page<SanPhamChiTietResponse> getAllPage(Pageable pageable) {
        return spctMapper.pageEntityToPageResponse(spctRepository.getAllPage(pageable));
    }

    @Override
    public List<SanPhamChiTietResponse> getAllListBySanPhamId(Long idSP) {
        return spctMapper.listEntityToListResponse(spctRepository.findByProductIdList(idSP));
    }

    @Override
    public Page<SanPhamChiTietResponse> getAllListBySanPhamId(Long idSP, Pageable pageable) {
        return spctMapper.pageEntityToPageResponse(spctRepository.findByProductIdPage(idSP, pageable));
    }

    @Override
    public SanPhamChiTietResponse add(SanPhamChiTietCreate create) {
        SanPhamChiTiet spct = spctMapper.createToEntity(create);
        beforeAdd(spct, create);
        return spctMapper.entityToResponse(spctRepository.save(spct));
    }

    @Override
    public List<SanPhamChiTietResponse> addList(List<SanPhamChiTietCreate> createList) {
        List<SanPhamChiTietResponse> responses = new ArrayList<>();
        for (SanPhamChiTietCreate create : createList) {
            responses.add(add(create));
        }
        return responses;
    }

    @Override
    public SanPhamChiTietResponse update(SanPhamChiTietUpdate update) {
        Optional<SanPhamChiTiet> result = spctRepository.findById(update.getId());
        if (!result.isPresent())
            return null;
        SanPhamChiTiet spct = result.get();
        SanPhamChiTiet entity = spctMapper.updateToEntity(update, spct);
        beforeUpdate(spct, update);
        return spctMapper.entityToResponse(spctRepository.save(entity));
    }

    @Override
    public void delete(Long idSPCT) {
        spctRepository.deleteById(idSPCT);
    }

    @Override
    public SanPhamChiTietResponse findById(Long idSPCT) {
        Optional<SanPhamChiTiet> result = spctRepository.findById(idSPCT);
        if (result.isPresent())
            return spctMapper.entityToResponse(result.get());
        return null;
    }

    @Override
    public List<SanPhamChiTietResponse> findByFilter(FindSanPhamChiTietByFilter filter) {
        Specification<SanPhamChiTiet> spec = Specification.where(specificationInner.listIntegerEquals("trangThai", ConvertStringToArray.toArray(filter.getTrangThai())))
                .and(specificationInner.timestampBefore("ngayTao", filter.getNgayTaoTruoc()))
                .and(specificationInner.timestampAfter("ngayTao", filter.getNgayTaoSau()))
                .and(specificationInner.timestampBefore("ngaySua", filter.getNgaySuaTruoc()))
                .and(specificationInner.timestampAfter("ngaySua", filter.getNgaySuaSau()))
                .and(specificationInner.bigDecimalLessThanOrEquals("giaBan", filter.getGiaNhoHon()))
                .and(specificationInner.bigDecimalGreaterThanOrEquals("giaBan", filter.getGiaLonHon()))
                .and(specificationInner.listStringEquals("ma", ConvertStringToArray.toArray(filter.getMaSanPhamChiTiet())))
//                //san pham
                .and(specificationJoin.listStringEquals(SanPham.class, "sanPham", "ma", ConvertStringToArray.toArray(filter.getMaSanPham())))
                .and(specificationJoin.listStringLike(SanPham.class, "sanPham", "ten", ConvertStringToArray.toArray(filter.getTenSanPham())))
//                // nhu cau - thuong hieu
                .and(specificationJoin.hasNhuCau(ConvertStringToArray.toArray(filter.getNhuCau())))
                .and(specificationJoin.hasThuongHieu(ConvertStringToArray.toArray(filter.getThuongHieu())))
//                // thuoc tinh
                .and(specificationJoin.listLongEquals(CPU.class, "cpu", "id", ConvertStringToArray.toArray(filter.getCpu())))
                .and(specificationJoin.listLongEquals(MauSac.class, "mauSac", "id", ConvertStringToArray.toArray(filter.getMauSac())))
                .and(specificationJoin.listLongEquals(RAM.class, "ram", "id", ConvertStringToArray.toArray(filter.getRam())))
                .and(specificationJoin.listLongEquals(VGA.class, "vga", "id", ConvertStringToArray.toArray(filter.getVga())))
                .and(specificationJoin.listLongEquals(Webcam.class, "webcam", "id", ConvertStringToArray.toArray(filter.getWebcam())))
                .and(specificationJoin.listLongEquals(OCung.class, "oCung", "id", ConvertStringToArray.toArray(filter.getOCung())))
                .and(specificationJoin.listLongEquals(ManHinh.class, "manHinh", "id", ConvertStringToArray.toArray(filter.getManHinh())))
                .and(specificationJoin.listLongEquals(HeDieuHanh.class, "heDieuHanh", "id", ConvertStringToArray.toArray(filter.getHeDieuHanh())))
                .and(specificationJoin.listLongEquals(BanPhim.class, "banPhim", "id", ConvertStringToArray.toArray(filter.getBanPhim())));
        List<SanPhamChiTiet> list = spctRepository.findAll(spec);
        return spctMapper.listEntityToListResponse(list);
    }

    @Override
    public boolean isExistSanPhamChiTietByCreate(SanPhamChiTietCreate create) {
        List<SanPhamChiTiet> check = spctRepository.isExistSanPhamChiTietForCreate(
                create.getSanPhamId(),
                create.getBanPhimId(),
                create.getCpuId(),
                create.getVgaId(),
                create.getHeDieuHanhId(),
                create.getManHinhId(),
                create.getRamId(),
                create.getOCungId(),
                create.getWebcamId(),
                create.getMauSacId()
        );
        if (check.size() > 0)
            return true;
        return false;
    }

    @Override
    public boolean isExistSanPhamChiTietByUpdate(SanPhamChiTietUpdate update) {
        List<SanPhamChiTiet> check = spctRepository.isExistSanPhamChiTietForUpdate(
                update.getSanPhamId(),
                update.getBanPhimId(),
                update.getCpuId(),
                update.getVgaId(),
                update.getHeDieuHanhId(),
                update.getManHinhId(),
                update.getRamId(),
                update.getOCungId(),
                update.getWebcamId(),
                update.getId(),
                update.getMauSacId()
        );
        if (check.size() > 0)
            return true;
        return false;
    }

    public void beforeAdd(SanPhamChiTiet spct, SanPhamChiTietCreate create) {
        String ma = "";
        do {
            ma = GenerateCode.generateCode(GenerateCode.SAN_PHAM_CHI_TIET_PREFIX);
        } while (spctRepository.isExistCode(ma).size() > 0);
        RAM ram = ramService.findEntityById(create.getRamId());
        CPU cpu = cpuService.findEntityById(create.getCpuId());
        VGA vga = vgaService.findEntityById(create.getVgaId());
        ManHinh manHinh = manHinhService.findEntityById(create.getManHinhId());
        BanPhim banPhim = banPhimService.findEntityById(create.getBanPhimId());
        HeDieuHanh heDieuHanh = heDieuHanhService.findEntityById(create.getHeDieuHanhId());
        OCung oCung = oCungService.findEntityById(create.getOCungId());
        MauSac mauSac = mauSacService.findEntityById(create.getMauSacId());
        Webcam webcam = webcamService.findEntityById(create.getWebcamId());
        SanPham sanPham = sanPhamService.findEntityById(create.getSanPhamId());
        spct.setMa(ma);
        spct.setRam(ram);
        spct.setCpu(cpu);
        spct.setVga(vga);
        spct.setManHinh(manHinh);
        spct.setBanPhim(banPhim);
        spct.setHeDieuHanh(heDieuHanh);
        spct.setOCung(oCung);
        spct.setMauSac(mauSac);
        spct.setWebcam(webcam);
        spct.setSanPham(sanPham);
    }

    public void beforeUpdate(SanPhamChiTiet spct, SanPhamChiTietUpdate update) {
        RAM ram = ramService.findEntityById(update.getRamId());
        CPU cpu = cpuService.findEntityById(update.getCpuId());
        VGA vga = vgaService.findEntityById(update.getVgaId());
        ManHinh manHinh = manHinhService.findEntityById(update.getManHinhId());
        BanPhim banPhim = banPhimService.findEntityById(update.getBanPhimId());
        HeDieuHanh heDieuHanh = heDieuHanhService.findEntityById(update.getHeDieuHanhId());
        OCung oCung = oCungService.findEntityById(update.getOCungId());
        MauSac mauSac = mauSacService.findEntityById(update.getMauSacId());
        Webcam webcam = webcamService.findEntityById(update.getWebcamId());
        SanPham sanPham = sanPhamService.findEntityById(update.getSanPhamId());
        spct.setRam(ram);
        spct.setCpu(cpu);
        spct.setVga(vga);
        spct.setManHinh(manHinh);
        spct.setBanPhim(banPhim);
        spct.setHeDieuHanh(heDieuHanh);
        spct.setOCung(oCung);
        spct.setMauSac(mauSac);
        spct.setWebcam(webcam);
        spct.setSanPham(sanPham);
    }
}
