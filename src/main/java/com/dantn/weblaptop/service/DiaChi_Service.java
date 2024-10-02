package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.CreateDiaChi;
import com.dantn.weblaptop.dto.request.update_request.UpdateDiaChi;
import com.dantn.weblaptop.dto.response.DiaChi_Response;
import com.dantn.weblaptop.entity.khachhang.DiaChi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DiaChi_Service {
    DiaChi_Response createDiaChi(CreateDiaChi createDiaChi);

    DiaChi_Response updateDiaChi(Long id, UpdateDiaChi updateDiaChi);

    DiaChi_Response getDiaChiById(Long id);

    List<DiaChi_Response> getAllDiaChiByIdKhachHang(Long idKhachHang);

    void deleteDiaChi(Long id);

    void defauldiaChi(Long id, long idKhachHang);

    void unDefauldiaChi(Long id, long idKhachHang);

    DiaChi_Response getDiaChiDefauldOfIdKhachHang(Long id);
}
