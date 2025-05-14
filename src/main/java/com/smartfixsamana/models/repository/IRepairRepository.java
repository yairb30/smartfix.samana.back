package com.smartfixsamana.models.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.smartfixsamana.models.entity.Repair;

public interface IRepairRepository extends CrudRepository<Repair, Long> {

       @Query("SELECT r FROM Repair r WHERE " +
           "LOWER(r.customer.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(r.customer.lastname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(r.phone.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(r.phone.model) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Repair> findRepairsByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
