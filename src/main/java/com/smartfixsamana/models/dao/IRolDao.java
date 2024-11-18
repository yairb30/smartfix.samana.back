package com.smartfixsamana.models.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.smartfixsamana.models.entity.Role;


public interface IRolDao extends CrudRepository<Role, Long>{

    Optional<Role>  findByName(String name);

}
