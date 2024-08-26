package com.dantn.weblaptop.mapper;

import com.dantn.weblaptop.dto.request.create_request.CreateLichLamViec;
import com.dantn.weblaptop.dto.request.update_request.UpdateLichLamViec;
import com.dantn.weblaptop.dto.response.LichLamViecResponse;
import com.dantn.weblaptop.entity.nhanvien.LichLamViec;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper
public interface LichLamViec_Mapper {
    LichLamViec createLichLamViec(CreateLichLamViec createLichLamViec);
    LichLamViec updateLichLamViec(UpdateLichLamViec updateLichLamViec);
    LichLamViecResponse entityToResponseLichLamViec (LichLamViec lichLamViec);
    List<LichLamViecResponse> listLichLamViecToResponse (List<LichLamViec> listLichLamViec);
}
