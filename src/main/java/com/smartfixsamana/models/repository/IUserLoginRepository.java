package com.smartfixsamana.models.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.smartfixsamana.models.entity.UserLogin;


public interface IUserLoginRepository extends CrudRepository<UserLogin, Long>{

    Optional<UserLogin> findByUsername(String username);

}
