package com.dantn.weblaptop.mapper;


import com.dantn.weblaptop.dto.request.create_request.CreateVaiTro_Request;
import com.dantn.weblaptop.dto.request.update_request.UpdateVaiTro_Request;
import com.dantn.weblaptop.dto.response.VaiTro_Response;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;



public interface VaiTro_Mapper {

    VaiTro CreateToEntity(CreateVaiTro_Request dto);


    VaiTro UpdateToEntity(UpdateVaiTro_Request dto);


    VaiTro_Response toResponse(VaiTro entity);
}

