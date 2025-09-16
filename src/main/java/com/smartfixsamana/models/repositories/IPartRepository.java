package com.smartfixsamana.models.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.smartfixsamana.models.entities.Part;

public interface IPartRepository extends CrudRepository<Part, Long> {

    @Query("SELECT p FROM Part p " +
            "WHERE LOWER(CONCAT(p.partCatalog.name, ' ', p.phone.brand, ' ', p.phone.model)) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Part> findPartsByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
