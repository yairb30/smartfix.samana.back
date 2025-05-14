package com.smartfixsamana.models.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.smartfixsamana.models.entity.Customer;

public interface ICustomerRepository extends CrudRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE " + "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
            + "LOWER(c.lastname) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Customer> findCustomersByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
