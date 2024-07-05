package com.dantn.weblaptop.sanpham.generics;

import org.springframework.data.domain.Page;

import java.util.List;

public interface IGenericsMapper<E, C, U, R> {
    E createToEntity(C create);

    E updateToEntity(U update, E entity);

    R entityToResponse(E entity);

    List<R> listEntityToListResponse(List<E> entityList);

    Page<R> pageEntityToPageResponse(Page<E> entityPage);
}
