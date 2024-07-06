package com.dantn.weblaptop.VaiTro.Mapper;


import com.dantn.weblaptop.VaiTro.Dto.Request.CreateVaiTro_Request;
import com.dantn.weblaptop.VaiTro.Dto.Request.UpdateVaiTro_Request;
import com.dantn.weblaptop.VaiTro.Dto.Response.VaiTro_Response;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;



public interface VaiTro_Mapper {

    VaiTro CreateToEntity(CreateVaiTro_Request dto);


    VaiTro UpdateToEntity(UpdateVaiTro_Request dto);


    VaiTro_Response toResponse(VaiTro entity);
}

