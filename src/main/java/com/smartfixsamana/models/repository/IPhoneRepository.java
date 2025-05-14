package com.smartfixsamana.models.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.smartfixsamana.models.entity.Phone;

public interface IPhoneRepository extends CrudRepository<Phone, Long> {

    @Query("SELECT p FROM Phone p WHERE " + "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
            + "LOWER(p.model) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Phone> findPhonesByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
