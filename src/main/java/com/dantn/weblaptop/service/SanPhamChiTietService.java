package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.FindSanPhamChiTietByFilter;
import com.dantn.weblaptop.dto.request.create_request.SanPhamChiTietCreate;
import com.dantn.weblaptop.dto.request.update_request.SPCTUpdateTemp;
import com.dantn.weblaptop.dto.request.update_request.SanPhamChiTietUpdate;
import com.dantn.weblaptop.dto.response.SPCTForGemini;
import com.dantn.weblaptop.dto.response.SanPhamChiTietClientDTO;
import com.dantn.weblaptop.dto.response.SanPhamChiTietResponse;
import com.dantn.weblaptop.repository.SanPhamChiTietRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SanPhamChiTietService {
    List<SanPhamChiTietResponse> getAllList();

    Page<SanPhamChiTietResponse> getAllPage(Pageable pageable);

    List<SanPhamChiTietResponse> getAllListBySanPhamId(Long idSP);

    Page<SanPhamChiTietResponse> getAllListBySanPhamId(Long idSP, Pageable pageable);

    SanPhamChiTietResponse add(SanPhamChiTietCreate create);

    List<SanPhamChiTietResponse> addList(List<SanPhamChiTietCreate> createList);

    SanPhamChiTietResponse update(SanPhamChiTietUpdate update);

    void delete(Long idSPCT);

    SanPhamChiTietResponse findById(Long idSPCT);

    List<SanPhamChiTietClientDTO> findByFilter(FindSanPhamChiTietByFilter filter);
    Page<SanPhamChiTietClientDTO> findByFilterPage(FindSanPhamChiTietByFilter filter, Pageable pageable);

    boolean isExistSanPhamChiTietByCreate(SanPhamChiTietCreate create);

    boolean isExistSanPhamChiTietByUpdate(SanPhamChiTietUpdate update);

    void updatePriceImage(SPCTUpdateTemp spct) throws Exception;

    void changeStatus(Long idSPCT, Integer status) throws Exception;

    void tatTrangThaiTheoIdSP(Long idSP);
    void batTrangThaiTheoIdSP(Long idSP);

    List<SPCTForGemini> findByFilterGemini(FindSanPhamChiTietByFilter filter);
}
