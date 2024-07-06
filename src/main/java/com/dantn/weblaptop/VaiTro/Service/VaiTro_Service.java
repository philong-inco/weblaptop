package com.dantn.weblaptop.VaiTro.Service;

import com.dantn.weblaptop.VaiTro.Dto.Request.CreateVaiTro_Request;
import com.dantn.weblaptop.VaiTro.Dto.Request.UpdateVaiTro_Request;
import com.dantn.weblaptop.VaiTro.Dto.Response.VaiTro_Response;
import com.dantn.weblaptop.constant.AccountStatus;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import com.dantn.weblaptop.nhanvien.Dto.Request.CreateNhanVien_Request;
import com.dantn.weblaptop.nhanvien.Dto.Request.UpdateNhanVien_Request;
import com.dantn.weblaptop.nhanvien.Dto.Response.NhanVien_Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface VaiTro_Service {
    VaiTro_Response create(CreateVaiTro_Request dto);
    VaiTro_Response update(UpdateVaiTro_Request dto);
    VaiTro_Response getById(Long id);
    List<VaiTro_Response> getAll();
    Page<VaiTro> pageAllVaiTro(Pageable pageable);
    void revertStatus(Long id);
}

