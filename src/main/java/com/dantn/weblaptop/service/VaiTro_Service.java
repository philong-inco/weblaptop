package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.CreateVaiTro_Request;
import com.dantn.weblaptop.dto.request.update_request.UpdateVaiTro_Request;
import com.dantn.weblaptop.dto.response.VaiTro_Response;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VaiTro_Service {
    VaiTro_Response create(CreateVaiTro_Request dto);
    VaiTro_Response update(UpdateVaiTro_Request dto, Long id);
    VaiTro_Response getById(Long id);
    List<VaiTro_Response> getAll();
    Page<VaiTro> pageAllVaiTro(Pageable pageable);
    void revertStatus(Long id);
    VaiTro_Response findVaiTroByIdNhanVienVaiTro(Long id);
}

