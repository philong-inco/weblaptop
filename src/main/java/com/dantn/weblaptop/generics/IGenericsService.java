package com.dantn.weblaptop.generics;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGenericsService<E, ID, C, U, R> {
    Page<R> getAllPage(Pageable pageable);

    List<R> getAllList();
    List<R> getAllListActive();

    Page<R> findByStatusPage(Integer status, Pageable pageable);

    List<R> findByStatusList(Integer status);

    Page<R> findByCodeOrNamePage(String keyword, Pageable pageable);

    List<R> findByCodeOrNameList(String keyword);

    R add(C create);

    R update(ID id, U update);

    void delete(ID id);

    E findEntityById(ID id);

    R findResponseById(ID id);

    boolean existByCode(String code);

    boolean existByName(String name);

    boolean existByNameAndDifferentId(String name, ID id);

}
