package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.SerialNumberCreate;
import com.dantn.weblaptop.dto.request.update_request.SerialNumberUpdate;
import com.dantn.weblaptop.dto.response.SerialNumberResponse;
import com.dantn.weblaptop.entity.sanpham.SerialNumber;
import com.dantn.weblaptop.repository.SerialNumberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SerialNumberService {

    SerialNumberResponse add(SerialNumberCreate create);
    SerialNumberResponse update(SerialNumberUpdate update);
    boolean delete(Long id);
    List<SerialNumberResponse> getAll();
    Page<SerialNumberResponse> getAllPage(Pageable pageable);
    boolean existByAdd(String ma);
    boolean existByUpdate(String ma, Long id);
    SerialNumberResponse findById(Long id);
    SerialNumberResponse findByMa(String ma);
    void deleteAllByIdSPCT(Long idSPCT);
    void changeStatusToSeriNumberDaBan(Long idSerialNumber);

}
