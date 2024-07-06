package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.entity.sanpham.NhuCau;
import com.dantn.weblaptop.entity.sanpham.SanPham;
import com.dantn.weblaptop.entity.sanpham.ThuongHieu;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.generics.IGenericsMapper;
import com.dantn.weblaptop.generics.IGenericsRepository;
import com.dantn.weblaptop.dto.request.create_request.NhuCauCreate;
import com.dantn.weblaptop.dto.request.update_request.NhuCauUpdate;
import com.dantn.weblaptop.dto.response.NhuCauResponse;
import com.dantn.weblaptop.dto.request.create_request.SanPhamCreate;
import com.dantn.weblaptop.dto.request.update_request.SanPhamUpdate;
import com.dantn.weblaptop.dto.response.SanPhamResponse;
import com.dantn.weblaptop.dto.request.create_request.ThuongHieuCreate;
import com.dantn.weblaptop.dto.request.update_request.ThuongHieuUpdate;
import com.dantn.weblaptop.dto.response.ThuongHieuResponse;
import com.dantn.weblaptop.util.GenerateCode;
import org.springframework.stereotype.Service;

@Service
public class SanPhamService extends GenericsService<SanPham, Long, SanPhamCreate, SanPhamUpdate, SanPhamResponse> {

    private final GenericsService<ThuongHieu, Long, ThuongHieuCreate, ThuongHieuUpdate, ThuongHieuResponse> serviceThuongHieu;
    private final GenericsService<NhuCau, Long, NhuCauCreate, NhuCauUpdate, NhuCauResponse> serviceNhuCau;

    public SanPhamService(IGenericsRepository<SanPham, Long> genericsRepository, IGenericsMapper<SanPham, SanPhamCreate, SanPhamUpdate, SanPhamResponse> genericsMapper, GenericsService<ThuongHieu, Long, ThuongHieuCreate, ThuongHieuUpdate, ThuongHieuResponse> serviceThuongHieu, GenericsService<NhuCau, Long, NhuCauCreate, NhuCauUpdate, NhuCauResponse> serviceNhuCau) {
        super(genericsRepository, genericsMapper);
        this.serviceThuongHieu = serviceThuongHieu;
        this.serviceNhuCau = serviceNhuCau;
    }

    @Override
    public void convertCreateToEntity(SanPhamCreate create, SanPham entity) {
        String ma;
        do {
            ma = GenerateCode.generateCode(GenerateCode.VGA_PREFIX);
        } while (existByCode(ma));
        ThuongHieu thuongHieu = serviceThuongHieu.findEntityById(create.getThuongHieuId());
        NhuCau nhuCau = serviceNhuCau.findEntityById(create.getNhuCauId());
        entity.setMa(ma);
        entity.setThuongHieu(thuongHieu);
        entity.setNhuCau(nhuCau);
    }

    @Override
    public void convertUpdateToEntity(SanPhamUpdate update, SanPham entity) {
        ThuongHieu thuongHieu = serviceThuongHieu.findEntityById(update.getThuongHieuId());
        NhuCau nhuCau = serviceNhuCau.findEntityById(update.getNhuCauId());
        entity.setThuongHieu(thuongHieu);
        entity.setNhuCau(nhuCau);
    }

    @Override
    public void beforeAdd(SanPhamCreate create) {
        if (genericsRepository.isExistName(create.getTen().trim()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }

    @Override
    public void beforeUpdate(SanPhamUpdate update) {
        if (genericsRepository.isExistNameAndDifferentId(update.getTen().trim(), update.getId()).size() > 0)
            throw new RuntimeException("Tên đã tồn tại");
    }
}
