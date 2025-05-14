package com.smartfixsamana.models.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.smartfixsamana.models.entity.Part;

public interface IPartRepository extends CrudRepository<Part, Long> {
 @Query("SELECT p FROM Part p WHERE " +
           "LOWER(p.partCatalog.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.phone.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.phone.model) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Part> findPartsByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
