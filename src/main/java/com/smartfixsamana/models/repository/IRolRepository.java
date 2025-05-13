package com.smartfixsamana.models.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.smartfixsamana.models.entity.Role;


public interface IRolRepository extends CrudRepository<Role, Long>{

    Optional<Role>  findByName(String name);

}
