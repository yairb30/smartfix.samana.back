package com.smartfixsamana.models.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.smartfixsamana.models.entity.Repair;

public interface IRepairRepository extends CrudRepository<Repair, Long> {

    @Query("SELECT r FROM Repair r WHERE r.customer.name LIKE %:keyword% OR r.customer.lastname LIKE %:keyword%")
    List<Repair> findByNameLastnameContaining(@Param("keyword") String keyword);

    @Query("SELECT r FROM Repair r WHERE r.phone.brand LIKE %:keyword% OR r.phone.model LIKE %:keyword%")
    List<Repair> findByBrandModelContaining(@Param("keyword") String keyword);

    Page<Repair> findAll(Pageable pageable);

}
