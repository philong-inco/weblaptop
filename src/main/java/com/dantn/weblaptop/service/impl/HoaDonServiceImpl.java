package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.dantn.weblaptop.dto.request.create_request.CreateLichSuHoaDonRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateHoaDonRequest;
import com.dantn.weblaptop.dto.response.HoaDonResponse;
import com.dantn.weblaptop.dto.response.Meta;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.hoadon.LichSuHoaDon;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.mapper.impl.HoaDonMapper;
import com.dantn.weblaptop.repository.HoaDonRepository;
import com.dantn.weblaptop.repository.NhanVienRepository;
import com.dantn.weblaptop.service.HoaDonService;
import com.dantn.weblaptop.service.LichSuHoaDonService;
import com.dantn.weblaptop.util.GenerateCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class HoaDonServiceImpl implements HoaDonService {
    HoaDonRepository billRepository;
    LichSuHoaDonService billHistoryService;
    NhanVienRepository employeeRepository;

    @Override
    public ResultPaginationResponse getBillPage(Optional<String> page, Optional<String> size) {
        String sPage = page.isPresent() ? page.get() : "0";
        String sSize = size.isPresent() ? size.get() : "5";
        Pageable pageable = PageRequest.of(Integer.parseInt(sPage), Integer.parseInt(sSize), Sort.by("id").descending());
        Page<HoaDon> billHistoryPage = billRepository.findAll(pageable);
        Page<HoaDonResponse> responses = billHistoryPage.map(bill -> HoaDonMapper.toHoaDonResponse(bill));

        Meta meta = Meta.builder()
                .page(responses.getNumber())
                .pageSize(responses.getSize())
                .pages(responses.getTotalPages())
                .total(responses.getTotalElements())
                .build();

        ResultPaginationResponse response = ResultPaginationResponse
                .builder()
                .meta(meta)
                .result(responses.getContent())
                .build();
        return response;
    }

    @Override
    public HoaDonResponse createBill() throws AppException {
        List<HoaDon> billListStatusPending = billRepository.findByTrangThaiAndLoaiHoaDon(HoaDonStatus.getHoaDonStatusEnum("Đơn mới"), 0);
        if (billListStatusPending.size() >= 5) {
            throw new AppException("Chỉ được tạo 5 hóa đơn");
        }
        NhanVien existingEmployee = employeeRepository.findById(1L).get();
        // save
        HoaDon newBill = new HoaDon();
        newBill.setMa(GenerateCode.generateHoaDon());
        newBill.setNhanVien(existingEmployee);
        newBill.setTrangThai(HoaDonStatus.DON_MOI);
        newBill.setLoaiHoaDon(0);// 0 : Tại quầy - 1 : Online
        HoaDonResponse response = HoaDonMapper.toHoaDonResponse(billRepository.save(newBill));

        CreateLichSuHoaDonRequest billHistoryRequest = new CreateLichSuHoaDonRequest();
        billHistoryRequest.setIdHoaDon(response.getId());
        // TT LSHD : 0 -tạo mới | 1 - Cập nhập : 2 - thanh toán :
        billHistoryRequest.setTrangThai(0);
        // sủa khi có security
        billHistoryRequest.setIdNhanVien(1L);
        // save
        billHistoryService.create(billHistoryRequest);

        log.info("Bill History : " + billHistoryRequest);
        return response;
    }

    @Override
    public HoaDonResponse updateBill(Long id, UpdateHoaDonRequest request) {
        return null;
    }

    @Override
    public List<HoaDonResponse> listBillByStatusAndType(String status, Integer type) {
        HoaDonStatus billStatus = HoaDonStatus.getHoaDonStatusEnumByKey(status);
        List<HoaDonResponse> listBill = billRepository.findByTrangThaiAndLoaiHoaDon(billStatus,type)
                .stream().map(
                        bill ->HoaDonMapper.toHoaDonResponse(bill)
                ).toList();
        return listBill;
    }

    @Override
    public void updateStatus(Long id , String status) throws AppException {
        Optional<HoaDon> optional = billRepository.findById(id);
        if(optional.isPresent()){
            HoaDon bill = optional.get();
            bill.setTrangThai(HoaDonStatus.getHoaDonStatusEnumByKey(status));
            CreateLichSuHoaDonRequest billHistoryRequest = new CreateLichSuHoaDonRequest();
            billHistoryRequest.setIdHoaDon(bill.getId());
            billHistoryRequest.setTrangThai(8);
            // sủa khi có security
            billHistoryRequest.setIdNhanVien(1L);
            // save
            billHistoryService.create(billHistoryRequest);
            billRepository.save(bill);
        }
    }
}
