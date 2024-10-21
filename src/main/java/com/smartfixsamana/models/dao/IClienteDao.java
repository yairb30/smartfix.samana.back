package com.smartfixsamana.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.smartfixsamana.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long> {

    List<Cliente> findByNombre(String nombre);

    Page<Cliente> findAll(Pageable pageable);

}
