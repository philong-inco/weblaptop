package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.FindSanPhamFilterByName;
import com.dantn.weblaptop.dto.request.create_request.SanPhamCreate;
import com.dantn.weblaptop.dto.request.update_request.SanPhamUpdate;
import com.dantn.weblaptop.dto.request.update_request.UpdateSPAndSPCTDTO;
import com.dantn.weblaptop.dto.response.ResponseLong;
import com.dantn.weblaptop.dto.response.SanPhamClientDTO;
import com.dantn.weblaptop.dto.response.SanPhamResponse;
import com.dantn.weblaptop.entity.sanpham.SanPham;
import com.dantn.weblaptop.generics.GenericsController;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.service.impl.SanPhamService;
import com.dantn.weblaptop.util.ConvertStringToArray;
import com.dantn.weblaptop.util.FakeDataForClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
// commit by long
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/san-pham/")
public class SanPhamController extends GenericsController<SanPham, Long, SanPhamCreate, SanPhamUpdate, SanPhamResponse> {

    private final SanPhamService sanPhamService;

    public SanPhamController(@Qualifier("sanPhamService") GenericsService<SanPham, Long, SanPhamCreate, SanPhamUpdate, SanPhamResponse> genericsService,
                             SanPhamService sanPhamService) {
        super(genericsService);
        this.sanPhamService = sanPhamService;
    }

    @GetMapping("/change-status")
    public ResponseEntity<ResponseLong<Boolean>> setStatus(
            @RequestParam("id") String idStr,
            @RequestParam("status") String statusStr) {
        try {
            Long id = Long.valueOf(idStr);
            Integer status = Integer.valueOf(statusStr);
            if (!(status == 1 || status == 0))
                throw new RuntimeException("Status invalid");
            if (!sanPhamService.setStatus(id, status))
                throw new RuntimeException("Set status failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseLong<>(200, "Change status successfully", true
                            , null, null, null, null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseLong<>(999, "Params invaild", false
                            , null, null, null, null)
            );
        }
    }

    @GetMapping("find/filter-name")
    public ResponseEntity<ResponseLong<List<SanPhamResponse>>> findByFilterName(
            @RequestParam(name = "page", required = false, defaultValue = "0") String pageStr,
            @RequestParam(name = "size", required = false, defaultValue = "10") String sizeStr,
            @RequestParam(name = "tenSanPham", required = false, defaultValue = "") String tenSanPham,
            @RequestParam(name = "ma", required = false, defaultValue = "") String ma,
            @RequestParam(name = "ngayTaoTruoc", required = false, defaultValue = "") String ngayTaoTruoc,
            @RequestParam(name = "ngayTaoSau", required = false, defaultValue = "") String ngayTaoSau,
            @RequestParam(name = "ngaySuaTruoc", required = false, defaultValue = "") String ngaySuaTruoc,
            @RequestParam(name = "ngaySuaSau", required = false, defaultValue = "") String ngaySuaSau,
            @RequestParam(name = "trangThai", required = false, defaultValue = "") String trangThai,
            @RequestParam(value = "tenNhuCau", required = false, defaultValue = "") String tenNhuCau,
            @RequestParam(value = "tenThuongHieu", required = false, defaultValue = "") String tenThuongHieu,
            @RequestParam(value = "tenRam", required = false, defaultValue = "") String tenRam,
            @RequestParam(value = "tenMau", required = false, defaultValue = "") String tenMau,
            @RequestParam(value = "tenCPU", required = false, defaultValue = "") String tenCPU,
            @RequestParam(value = "tenVGA", required = false, defaultValue = "") String tenVGA,
            @RequestParam(value = "tenWebcam", required = false, defaultValue = "") String tenWebcam,
            @RequestParam(value = "tenOCung", required = false, defaultValue = "") String tenOCung,
            @RequestParam(value = "tenManHinh", required = false, defaultValue = "") String tenManHinh,
            @RequestParam(value = "tenHeDieuHanh", required = false, defaultValue = "") String tenHeDieuHanh,
            @RequestParam(value = "tenBanPhim", required = false, defaultValue = "") String tenBanPhim
    ) {
        Pageable pageable;
        try {
            pageable = PageRequest.of(Integer.valueOf(pageStr), Integer.valueOf(sizeStr));
        } catch (Exception e) {
            pageable = PageRequest.of(0, 10);
        }
        FindSanPhamFilterByName filter = FindSanPhamFilterByName.builder()
                .tenSanPham(tenSanPham)
                .ma(ma)
                .trangThai(trangThai)
                .ngayTaoTruoc(ngayTaoTruoc)
                .ngayTaoSau(ngayTaoSau)
                .ngaySuaTruoc(ngaySuaTruoc)
                .ngaySuaSau(ngaySuaSau)
                .tenNhuCau(ConvertStringToArray.toArray(tenNhuCau))
                .tenThuongHieu(ConvertStringToArray.toArray(tenThuongHieu))
                .tenRam(ConvertStringToArray.toArray(tenRam))
                .tenMau(ConvertStringToArray.toArray(tenMau))
                .tenCPU(ConvertStringToArray.toArray(tenCPU))
                .tenVGA(ConvertStringToArray.toArray(tenVGA))
                .tenWebcam(ConvertStringToArray.toArray(tenWebcam))
                .tenOCung(ConvertStringToArray.toArray(tenOCung))
                .tenManHinh(ConvertStringToArray.toArray(tenManHinh))
                .tenHeDieuHanh(ConvertStringToArray.toArray(tenHeDieuHanh))
                .tenBanPhim(ConvertStringToArray.toArray(tenBanPhim))
                .build();

        Page<SanPhamResponse> pageResult = sanPhamService.findWithFilterByName(filter, pageable);
        ResponseLong<List<SanPhamResponse>> result = new ResponseLong<>(
                200, "Find successfully",
                pageResult.getContent(),
                String.valueOf(pageResult.getNumber()),
                String.valueOf(pageResult.getSize()),
                String.valueOf(pageResult.getTotalPages()),
                String.valueOf(pageResult.getTotalElements()));
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("find/filter-id")
    public ResponseEntity<ResponseLong<List<SanPhamResponse>>> findByFilterId(
            @RequestParam(name = "page", required = false, defaultValue = "0") String pageStr,
            @RequestParam(name = "size", required = false, defaultValue = "10") String sizeStr,
            @RequestParam(name = "tenSanPham", required = false, defaultValue = "") String tenSanPham,
            @RequestParam(name = "ma", required = false, defaultValue = "") String ma,
            @RequestParam(name = "ngayTaoTruoc", required = false, defaultValue = "") String ngayTaoTruoc,
            @RequestParam(name = "ngayTaoSau", required = false, defaultValue = "") String ngayTaoSau,
            @RequestParam(name = "ngaySuaTruoc", required = false, defaultValue = "") String ngaySuaTruoc,
            @RequestParam(name = "ngaySuaSau", required = false, defaultValue = "") String ngaySuaSau,
            @RequestParam(name = "trangThai", required = false, defaultValue = "") String trangThai,
            @RequestParam(value = "idNhuCau", required = false, defaultValue = "") String tenNhuCau,
            @RequestParam(value = "idThuongHieu", required = false, defaultValue = "") String tenThuongHieu,
            @RequestParam(value = "idRam", required = false, defaultValue = "") String tenRam,
            @RequestParam(value = "idMauSac", required = false, defaultValue = "") String tenMau,
            @RequestParam(value = "idCPU", required = false, defaultValue = "") String tenCPU,
            @RequestParam(value = "idVGA", required = false, defaultValue = "") String tenVGA,
            @RequestParam(value = "idWebcam", required = false, defaultValue = "") String tenWebcam,
            @RequestParam(value = "idOCung", required = false, defaultValue = "") String tenOCung,
            @RequestParam(value = "idManHinh", required = false, defaultValue = "") String tenManHinh,
            @RequestParam(value = "idHeDieuHanh", required = false, defaultValue = "") String tenHeDieuHanh,
            @RequestParam(value = "idBanPhim", required = false, defaultValue = "") String tenBanPhim
    ) {
        Pageable pageable;
        try {
            pageable = PageRequest.of(Integer.valueOf(pageStr), Integer.valueOf(sizeStr), Sort.by(Sort.Direction.DESC, "ngayTao"));
        } catch (Exception e) {
            pageable = PageRequest.of(0, 10);
        }
        FindSanPhamFilterByName filter = FindSanPhamFilterByName.builder()
                .tenSanPham(tenSanPham)
                .ma(ma)
                .trangThai(trangThai)
                .ngayTaoTruoc(ngayTaoTruoc)
                .ngayTaoSau(ngayTaoSau)
                .ngaySuaTruoc(ngaySuaTruoc)
                .ngaySuaSau(ngaySuaSau)
                .tenNhuCau(ConvertStringToArray.toArray(tenNhuCau))
                .tenThuongHieu(ConvertStringToArray.toArray(tenThuongHieu))
                .tenRam(ConvertStringToArray.toArray(tenRam))
                .tenMau(ConvertStringToArray.toArray(tenMau))
                .tenCPU(ConvertStringToArray.toArray(tenCPU))
                .tenVGA(ConvertStringToArray.toArray(tenVGA))
                .tenWebcam(ConvertStringToArray.toArray(tenWebcam))
                .tenOCung(ConvertStringToArray.toArray(tenOCung))
                .tenManHinh(ConvertStringToArray.toArray(tenManHinh))
                .tenHeDieuHanh(ConvertStringToArray.toArray(tenHeDieuHanh))
                .tenBanPhim(ConvertStringToArray.toArray(tenBanPhim))
                .build();

        Page<SanPhamResponse> pageResult = sanPhamService.findWithFilterById(filter, pageable);
        ResponseLong<List<SanPhamResponse>> result = new ResponseLong<>(
                200, "Find successfully",
                pageResult.getContent(),
                String.valueOf(pageResult.getNumber()),
                String.valueOf(pageResult.getSize()),
                String.valueOf(pageResult.getTotalPages()),
                String.valueOf(pageResult.getTotalElements()));
        return ResponseEntity.ok().body(result);
    }

    // API fake data for client
    @GetMapping("find/client")
    public ResponseEntity<ResponseLong<List<SanPhamClientDTO>>> filterSanPhamForClient(
            @RequestParam(name = "page", required = false, defaultValue = "0") String pageStr,
            @RequestParam(name = "size", required = false, defaultValue = "20") String sizeStr,
            @RequestParam(name = "tenSanPham", required = false, defaultValue = "") String tenSanPham,
            @RequestParam(value = "idNhuCau", required = false, defaultValue = "") String tenNhuCau,
            @RequestParam(value = "idThuongHieu", required = false, defaultValue = "") String tenThuongHieu,
            @RequestParam(value = "idRam", required = false, defaultValue = "") String tenRam,
            @RequestParam(value = "idMauSac", required = false, defaultValue = "") String tenMau,
            @RequestParam(value = "idCPU", required = false, defaultValue = "") String tenCPU,
            @RequestParam(value = "idVGA", required = false, defaultValue = "") String tenVGA,
            @RequestParam(value = "idWebcam", required = false, defaultValue = "") String tenWebcam,
            @RequestParam(value = "idOCung", required = false, defaultValue = "") String tenOCung,
            @RequestParam(value = "idManHinh", required = false, defaultValue = "") String tenManHinh,
            @RequestParam(value = "idHeDieuHanh", required = false, defaultValue = "") String tenHeDieuHanh,
            @RequestParam(value = "idBanPhim", required = false, defaultValue = "") String tenBanPhim,
            @RequestParam(value = "dangKhuyenMai", required = false, defaultValue = "") String dangKhuyenmai // 1: true 0:false
    ) {
        Pageable pageable;
        try {
            pageable = PageRequest.of(Integer.valueOf(pageStr), Integer.valueOf(sizeStr), Sort.by(Sort.Direction.DESC, "ngayTao"));
        } catch (Exception e) {
            pageable = PageRequest.of(0, 10);
        }
//        FindSanPhamFilterByName filter = FindSanPhamFilterByName.builder()
//                .tenSanPham(tenSanPham)
//                .tenNhuCau(ConvertStringToArray.toArray(tenNhuCau))
//                .tenThuongHieu(ConvertStringToArray.toArray(tenThuongHieu))
//                .tenRam(ConvertStringToArray.toArray(tenRam))
//                .tenMau(ConvertStringToArray.toArray(tenMau))
//                .tenCPU(ConvertStringToArray.toArray(tenCPU))
//                .tenVGA(ConvertStringToArray.toArray(tenVGA))
//                .tenWebcam(ConvertStringToArray.toArray(tenWebcam))
//                .tenOCung(ConvertStringToArray.toArray(tenOCung))
//                .tenManHinh(ConvertStringToArray.toArray(tenManHinh))
//                .tenHeDieuHanh(ConvertStringToArray.toArray(tenHeDieuHanh))
//                .tenBanPhim(ConvertStringToArray.toArray(tenBanPhim))
//                .build();



//        Page<SanPhamResponse> pageResult = sanPhamService.findWithFilterById(filter, pageable);


        List<SanPhamClientDTO> dataFake = FakeDataForClient.fakeDataSanPhamForClient();
        List<SanPhamClientDTO> listResult = new ArrayList<>();
        int indexElement = Integer.valueOf(pageStr) * Integer.valueOf(sizeStr);
        if (indexElement + Integer.valueOf(sizeStr) > dataFake.size()){
            listResult = dataFake.subList(indexElement, dataFake.size() - 1);
        } else {
            listResult = dataFake.subList(indexElement, indexElement + Integer.valueOf(sizeStr));
        }
        int totalPagesss = 100 / Integer.valueOf(sizeStr);
        if (100 % Integer.valueOf(sizeStr) != 0) totalPagesss++;

        ResponseLong<List<SanPhamClientDTO>> result = new ResponseLong<>(
                200, "Find successfully",
                listResult,
                pageStr,
                sizeStr,
                String.valueOf(totalPagesss),
                String.valueOf(100));
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/updateSanPhamAndSPCT/{id}")
    public ResponseEntity<ResponseLong<SanPhamResponse>> updateSPAndSPCT(@RequestBody UpdateSPAndSPCTDTO request,
                                                                         @PathVariable("id") Long idSP){
        SanPhamResponse response = sanPhamService.updateSPAndSPCT(request, idSP);
        ResponseLong<SanPhamResponse> result = new ResponseLong<>(
                200, "Update successfully",
                response);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/exist-name-for-update")
    public ResponseEntity<ResponseLong<Boolean>> updateSPAndSPCT(@RequestParam("ten") String ten,
                                                                 @RequestParam("id") Long id){
        Boolean check = sanPhamService.existNameForUpdate(ten, id);
        ResponseLong<Boolean> result = new ResponseLong<>(
                200, "Check done",check);
        return ResponseEntity.ok().body(result);
    }
}