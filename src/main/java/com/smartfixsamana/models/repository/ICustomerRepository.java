package com.smartfixsamana.models.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.smartfixsamana.models.entity.Customer;

public interface ICustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByName(String name);

    Page<Customer> findAll(Pageable pageable);

}
