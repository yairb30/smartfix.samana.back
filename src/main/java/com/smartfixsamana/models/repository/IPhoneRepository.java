package com.smartfixsamana.models.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.smartfixsamana.models.entity.Phone;

public interface IPhoneRepository extends CrudRepository<Phone, Long> {

    List<Phone> findByBrand(String brand);

    List<Phone> findByModel(String model);

    Page<Phone> findAll(Pageable pageable);

}
