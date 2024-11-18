package com.smartfixsamana.models.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.smartfixsamana.models.entity.Usuario;


public interface IUsuarioDao extends CrudRepository<Usuario, Long>{

    Optional<Usuario> findByUsername(String username);

}
