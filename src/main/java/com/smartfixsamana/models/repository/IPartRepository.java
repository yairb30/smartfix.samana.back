package com.smartfixsamana.models.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.smartfixsamana.models.entity.Part;

public interface IPartRepository extends CrudRepository<Part, Long> {

     @Query("SELECT r FROM Part r WHERE r.partCatalog.name LIKE %:keyword%")
    List<Part> findByNameContaining(@Param("keyword") String keyword);

    @Query("SELECT r FROM Part r WHERE r.phone.brand LIKE %:keyword% OR r.phone.model LIKE %:keyword%")
    List<Part> findByBrandModelContaining(@Param("keyword") String keyword);

    Page<Part> findAll(Pageable Pageable);
}
