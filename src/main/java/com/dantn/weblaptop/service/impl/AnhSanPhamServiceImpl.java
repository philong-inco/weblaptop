package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.AnhSanPhamCreate;
import com.dantn.weblaptop.dto.request.update_request.AnhSanPhamUpdate;
import com.dantn.weblaptop.dto.response.AnhSanPhamResponse;
import com.dantn.weblaptop.entity.sanpham.AnhSanPham;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import com.dantn.weblaptop.mapper.impl.AnhSanPhamMapper;
import com.dantn.weblaptop.repository.AnhSanPhamChiTietRepository;
import com.dantn.weblaptop.repository.SanPhamChiTietRepository;
import com.dantn.weblaptop.service.AnhSanPhamService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AnhSanPhamServiceImpl implements AnhSanPhamService {

    AnhSanPhamChiTietRepository repository;
    SanPhamChiTietRepository sanPhamChiTietRepository;
    AnhSanPhamMapper mapper;

    @Override
    public AnhSanPhamResponse create(AnhSanPhamCreate create) {
        SanPhamChiTiet spct = sanPhamChiTietRepository.findById(create.getIdSPCT()).get();
        if (null == spct)
            return null;
        AnhSanPham entity = mapper.createToEntity(create);
        entity.setSanPhamChiTiet(spct);
        return mapper.entityToResponse(repository.save(entity));
    }

    @Override
    public boolean delete(Long idAnhSanPham) {
        AnhSanPham entity = repository.findById(idAnhSanPham).get();
        if (null == entity)
            return true;
        repository.deleteById(idAnhSanPham);
        return true;
    }

    @Override
    public boolean deleteByIdSPCT(Long idSPCT) {
        List<AnhSanPham> listEntity = repository.findByIdSPCT(idSPCT);
        if (listEntity == null || listEntity.size() == 0)
            return true;
        for (AnhSanPham anh : listEntity) {
            repository.deleteById(anh.getId());
        }
        return true;
    }

    @Override
    public List<AnhSanPhamResponse> create(List<AnhSanPhamCreate> creates) {
        List<AnhSanPhamResponse> responses = new ArrayList<>();
        boolean check = true;
        try {
            for (AnhSanPhamCreate c : creates) {
                AnhSanPham entity = mapper.createToEntity(c);
                responses.add(mapper.entityToResponse(repository.save(entity)));
            }
        } catch (Exception e) {
            check = false;
        }

        if (!check)
            return null;
        return responses;
    }

    @Override
    public AnhSanPhamResponse update(AnhSanPhamUpdate update) {
        SanPhamChiTiet spct = sanPhamChiTietRepository.findById(update.getIdSPCT()).get();
        AnhSanPham entityOld = repository.findById(update.getId()).get();
        if (null == entityOld || spct == null)
            return null;
        AnhSanPham entityNew = mapper.updateToEntity(update, entityOld);
        entityNew.setSanPhamChiTiet(spct);
        return mapper.entityToResponse(repository.save(entityNew));
    }

    @Override
    public List<AnhSanPhamResponse> getAllBySPCTId(Long idSPCT) {
        List<AnhSanPham> listEntity = repository.findByIdSPCT(idSPCT);
        if (listEntity == null || listEntity.size() == 0)
            return null;
        return mapper.listEntityToListResponse(listEntity);
    }

    @Override
    public void deleteAllByIdSPCT(Long idSPCT) {
        repository.deleteAllByIdSPCT(idSPCT);
    }

    @Override
    public List<String> getAll() {
        List<AnhSanPham> list = repository.findAll();
        return list.stream().map(x -> x.getUrl())
                .collect(Collectors.toSet())
                .stream().toList();
    }
}
