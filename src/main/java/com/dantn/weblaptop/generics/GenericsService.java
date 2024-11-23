package com.dantn.weblaptop.generics;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public abstract class GenericsService<E, ID, C, U, R> implements IGenericsService<E, ID, C, U, R> {

    protected final IGenericsRepository<E, ID> genericsRepository;
    protected final IGenericsMapper<E, C, U, R> genericsMapper;

    public GenericsService(IGenericsRepository<E, ID> genericsRepository,
                           IGenericsMapper<E, C, U, R> genericsMapper) {
        this.genericsRepository = genericsRepository;
        this.genericsMapper = genericsMapper;
    }

    @Override
    public Page<R> getAllPage(Pageable pageable) {
        return genericsMapper.pageEntityToPageResponse(genericsRepository.getAllPage(pageable));
    }

    @Override
    public List<R> getAllList() {
        return genericsMapper.listEntityToListResponse(genericsRepository.getAllList());
    }

    @Override
    public List<R> getAllListActive() {
        return genericsMapper.listEntityToListResponse(genericsRepository.getAllListActive());
    }

    @Override
    public Page<R> findByStatusPage(Integer status, Pageable pageable) {
        return genericsMapper.pageEntityToPageResponse(genericsRepository.findByStatusPage(status, pageable));
    }

    @Override
    public List<R> findByStatusList(Integer status) {
        return genericsMapper.listEntityToListResponse(genericsRepository.findByStatusList(status));
    }

    @Override
    public Page<R> findByCodeOrNamePage(String keyword, Pageable pageable) {
        return genericsMapper.pageEntityToPageResponse(genericsRepository.findByCodeOrNamePage(keyword.toLowerCase(), pageable));
    }

    @Override
    public List<R> findByCodeOrNameList(String keyword) {
        return genericsMapper.listEntityToListResponse(genericsRepository.findByCodeOrNameList(keyword.toLowerCase()));
    }

    @Override
    public R add(C create) {
        beforeAdd(create);
        E entity = genericsMapper.createToEntity(create);
        convertCreateToEntity(create, entity);
        return genericsMapper.entityToResponse(genericsRepository.save(entity));
    }

    @Override
    public R update(ID id, U update) {
        beforeUpdate(update);
        E entity = genericsRepository.findById(id).get();
        if (entity == null)
            throw new RuntimeException();
        entity = genericsMapper.updateToEntity(update, entity);
        convertUpdateToEntity(update, entity);
        return genericsMapper.entityToResponse(genericsRepository.save(entity));
    }

    @Override
    public void delete(ID id) {
        genericsRepository.deleteById(id);
    }

    @Override
    public E findEntityById(ID id) {
        return genericsRepository.findById(id).get();
    }

    @Override
    public R findResponseById(ID id) {
        Optional<E> entity = genericsRepository.findById(id);
        if (entity.isPresent())
            return genericsMapper.entityToResponse(entity.get());
        return null;
    }

    @Override
    public boolean existByCode(String code) {
        List<E> result = genericsRepository.isExistCode(code.trim().toLowerCase());
        boolean check = result.size() > 0 ? true : false;
        return check;
    }

    @Override
    public boolean existByName(String name) {
        List<E> result = genericsRepository.isExistName(name.trim().toLowerCase());
        boolean check = result.size() > 0 ? true : false;
        return check;
    }

    @Override
    public boolean existByNameAndDifferentId(String name, ID id) {
        List<E> result = genericsRepository.isExistNameAndDifferentId(name.trim().toLowerCase(), id);
        boolean check = result.size() > 0 ? true : false;
        return check;
    }

    public abstract void convertCreateToEntity(C create, E entity);

    public abstract void convertUpdateToEntity(U update, E entity);

    public abstract void beforeAdd(C create);

    public abstract void beforeUpdate(U update);
}
