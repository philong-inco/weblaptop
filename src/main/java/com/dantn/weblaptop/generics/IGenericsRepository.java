package com.dantn.weblaptop.generics;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface IGenericsRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    Page<T> getAllPage(Pageable pageable);

    List<T> getAllList();

    Page<T> findByStatusPage(Integer status, Pageable pageable);

    List<T> findByStatusList(Integer status);

    Page<T> findByCodeOrNamePage(String keyword, Pageable pageable);

    List<T> findByCodeOrNameList(String keyword);

    List<T> isExistCode(String code);

    List<T> isExistName(String name);

    List<T> isExistNameAndDifferentId(String name, ID id);
}
