package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.FindSanPhamChiTietByFilter;
import com.dantn.weblaptop.dto.request.create_request.SanPhamChiTietCreate;
import com.dantn.weblaptop.dto.request.update_request.SanPhamChiTietUpdate;
import com.dantn.weblaptop.dto.response.ResponseLong;
import com.dantn.weblaptop.dto.response.SanPhamChiTietClientDTO;
import com.dantn.weblaptop.dto.response.SanPhamChiTietResponse;
import com.dantn.weblaptop.service.SanPhamChiTietService;
import com.dantn.weblaptop.util.FakeDataForClient;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/san-pham-chi-tiet/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SanPhamChiTietController {
    SanPhamChiTietService service;

    @GetMapping("all")
    public ResponseEntity<ResponseLong<List<SanPhamChiTietResponse>>> getAllPage(
            @RequestParam(value = "page", required = false, defaultValue = "0") String page,
            @RequestParam(value = "size", required = false, defaultValue = "5") String size
    ) {
        Pageable pageable;
        try {
            pageable = PageRequest.of(Integer.valueOf(page), Integer.valueOf(size));
        } catch (Exception e) {
            pageable = PageRequest.of(0, 5);
        }
        Page<SanPhamChiTietResponse> pageResult = service.getAllPage(pageable);
        return ResponseEntity.ok().body(new ResponseLong<>(
                200,
                "GET successfully",
                pageResult.getContent(),
                String.valueOf(pageResult.getNumber()),
                String.valueOf(pageResult.getSize()),
                String.valueOf(pageResult.getTotalPages()),
                String.valueOf(pageResult.getTotalElements())
        ));
    }

    @PostMapping("add")
    public ResponseEntity<ResponseLong<SanPhamChiTietResponse>> add(@RequestBody @Valid SanPhamChiTietCreate create) {
        if (service.isExistSanPhamChiTietByCreate(create)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseLong<>(
                            999, "Product detail is existed",
                            null, null,
                            null, null, null
                    )
            );
        }
        return ResponseEntity.ok().body(
                new ResponseLong<>(
                        200, "Created product detail!",
                        service.add(create), null,
                        null, null, null
                )
        );
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ResponseLong<SanPhamChiTietResponse>> update(@RequestBody @Valid SanPhamChiTietUpdate update,
                                                                       @PathVariable("id") String idStr) {
        try {
            Long id = Long.valueOf(idStr);
            if (id != update.getId())
                throw new RuntimeException("ID invalid");
            if (service.isExistSanPhamChiTietByUpdate(update)){
                throw new RuntimeException("Produc is exist");
            }
        } catch (Exception e) {
            throw new RuntimeException("ID invalid");
        }
        return ResponseEntity.ok().body(new ResponseLong<>(
                200, "Update successfully",
                service.update(update),
                null, null, null, null
        ));

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseLong<String>> delete(@PathVariable("id") String idStr) {
        try {
            Long id = Long.valueOf(idStr);
        } catch (Exception e) {
            throw new RuntimeException("ID invalid");
        }
        service.delete(Long.valueOf(idStr));
        return ResponseEntity.ok().body(new ResponseLong<>(
                200, "Delete successfully",
                null,
                null, null, null, null
        ));
    }

    @GetMapping("find/filter")
    public ResponseEntity<ResponseLong<List<SanPhamChiTietResponse>>> findByFilter(
            @RequestParam(value = "tenSP", required = false, defaultValue = "") String tenSP,
            @RequestParam(value = "maSP", required = false, defaultValue = "") String maSP,
            @RequestParam(value = "maSPCT", required = false, defaultValue = "") String maSPCT,
            @RequestParam(value = "trangThai", required = false, defaultValue = "") String trangThai,
            @RequestParam(value = "ngayTaoTruoc", required = false, defaultValue = "") String ngayTaoTruoc,
            @RequestParam(value = "ngayTaoSau", required = false, defaultValue = "") String ngayTaoSau,
            @RequestParam(value = "ngaySuaTruoc", required = false, defaultValue = "") String ngaySuaTruoc,
            @RequestParam(value = "ngaySuaSau", required = false, defaultValue = "") String ngaySuaSau,
            @RequestParam(value = "thuongHieu", required = false, defaultValue = "") String thuongHieu,
            @RequestParam(value = "nhuCau", required = false, defaultValue = "") String nhuCau,
            @RequestParam(value = "mauSac", required = false, defaultValue = "") String mauSac,
            @RequestParam(value = "ram", required = false, defaultValue = "") String ram,
            @RequestParam(value = "cpu", required = false, defaultValue = "") String cpu,
            @RequestParam(value = "vga", required = false, defaultValue = "") String vga,
            @RequestParam(value = "webcam", required = false, defaultValue = "") String webcam,
            @RequestParam(value = "oCung", required = false, defaultValue = "") String oCung,
            @RequestParam(value = "manHinh", required = false, defaultValue = "") String manHinh,
            @RequestParam(value = "heDieuHanh", required = false, defaultValue = "") String heDieuHanh,
            @RequestParam(value = "banPhim", required = false, defaultValue = "") String banPhim,
            @RequestParam(value = "giaNhoHon", required = false, defaultValue = "") String giaNhoHon,
            @RequestParam(value = "giaLonHon", required = false, defaultValue = "") String giaLonHon
    ) {

        FindSanPhamChiTietByFilter filter = FindSanPhamChiTietByFilter.builder()
                .tenSanPham(tenSP)
                .maSanPham(maSP)
                .maSanPhamChiTiet(maSPCT)
                .trangThai(trangThai)
                .ngayTaoSau(ngayTaoSau)
                .ngayTaoTruoc(ngayTaoTruoc)
                .ngaySuaSau(ngaySuaSau)
                .ngaySuaTruoc(ngaySuaTruoc)
                .thuongHieu(thuongHieu)
                .nhuCau(nhuCau)
                .ram(ram)
                .cpu(cpu)
                .vga(vga)
                .mauSac(mauSac)
                .webcam(webcam)
                .oCung(oCung)
                .manHinh(manHinh)
                .heDieuHanh(heDieuHanh)
                .banPhim(banPhim)
                .giaLonHon(giaLonHon)
                .giaNhoHon(giaNhoHon)
                .build();
        return ResponseEntity.ok().body(new ResponseLong<>(
                200, "Find successfully",
                service.findByFilter(filter),
                null, null, null, null
        ));
    }

    @PostMapping("/valid-for-add")
    public ResponseEntity<ResponseLong<Boolean>> validForAdd(@RequestBody @Valid SanPhamChiTietCreate create){
        if (!service.isExistSanPhamChiTietByCreate(create))
            return ResponseEntity.ok(new ResponseLong<>(
                    200,
                    "Hợp lệ",
                    true, null, null, null, null
            ));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseLong<>(
                999,
                "Biến thể đã tồn tại",
                true, null, null, null, null
        ));
    }

    @PostMapping("/valid-for-update")
    public ResponseEntity<ResponseLong<Boolean>> validForAdd(@RequestBody @Valid SanPhamChiTietUpdate update){
        if (!service.isExistSanPhamChiTietByUpdate(update))
            return ResponseEntity.ok(new ResponseLong<>(
                    200,
                    "Hợp lệ",
                    true, null, null, null, null
            ));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseLong<>(
                999,
                "Biến thể đã tồn tại",
                true, null, null, null, null
        ));
    }

    @GetMapping("/get-by-product-id")
    public ResponseEntity<ResponseLong<List<SanPhamChiTietResponse>>> getAllByProductId(
            @RequestParam(value = "idProduct", required = true) Long idProduct){
        List<SanPhamChiTietResponse> responses = service.getAllListBySanPhamId(idProduct);
        if ( responses == null || responses.size() == 0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseLong<>(
                    404, "Không có biến thể nào trên ID Product này",
                    null, null, null, null, null
            ));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseLong<>(
                200, "Get thành công",
                responses, null, null, null, null
        ));
    }

    @GetMapping("/get-by-productdetail-id")
    public ResponseEntity<ResponseLong<SanPhamChiTietResponse>> getAllByProductDetailId(
            @RequestParam(value = "idProductDetail", required = true) Long idProductDetail){
        SanPhamChiTietResponse responses = service.findById(idProductDetail);
        if (responses == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseLong<>(
                    404, "Không có biến thể nào",
            null, null, null, null, null
            ));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseLong<>(
                200, "Get thành công",
                responses, null, null, null, null
        ));
    }

    @GetMapping("get-SPCT-for-client/{idSP}")
    public ResponseEntity<ResponseLong<SanPhamChiTietClientDTO>> getSPCTForClient(@PathVariable("idSP") Long idSP){
        SanPhamChiTietClientDTO spct = FakeDataForClient.fakeDataSPCTForClient();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseLong<>(
                200, "Get thành công", spct
        ));
    }

    @GetMapping("get-SPCT-for-client-checked{idSP}")
    public ResponseEntity<ResponseLong<SanPhamChiTietClientDTO>> getSPCTForClientChecked(
                    @PathVariable("idSP") Long idSP,
                    @RequestParam("idMauSac") Long idMauSac,
                    @RequestParam("idCPU") Long idCPU,
                    @RequestParam("idRam") Long idRam,
                    @RequestParam("idOCung") Long idOCung
    ){
        SanPhamChiTietClientDTO spct = FakeDataForClient.fakeDataSPCTForClient();
        spct.setListUrlAnhSanPham("https://thegioiso365.vn/wp-content/uploads/2022/10/23-1-1536x864.jpg,https://thegioiso365.vn/wp-content/uploads/2022/10/7-5-1536x864.jpg,https://thegioiso365.vn/wp-content/uploads/2022/10/5-6-1536x864.jpg");
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseLong<>(
                200, "Lấy biến thể thành công", spct
        ));
    }

}
