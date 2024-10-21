package com.smartfixsamana.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.smartfixsamana.models.entity.Celular;

public interface ICelularDao extends CrudRepository<Celular, Long> {

    List<Celular> findByMarca(String marca);

    List<Celular> findByModelo(String modelo);

    Page<Celular> findAll(Pageable pageable);

}
