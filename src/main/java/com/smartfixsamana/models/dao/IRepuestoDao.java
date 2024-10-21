package com.smartfixsamana.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.smartfixsamana.models.entity.Repuesto;

public interface IRepuestoDao extends CrudRepository<Repuesto, Long> {

     @Query("SELECT r FROM Repuesto r WHERE r.elegirRepuesto.nombre LIKE %:keyword% OR r.elegirRepuesto.detalles LIKE %:keyword%")
    List<Repuesto> findByNombreRepuestoContaining(@Param("keyword") String keyword);

    @Query("SELECT r FROM Repuesto r WHERE r.elegirCelular.marca LIKE %:keyword% OR r.elegirCelular.modelo LIKE %:keyword%")
    List<Repuesto> findByCelularMarcaOrModeloContaining(@Param("keyword") String keyword);

    Page<Repuesto> findAll(Pageable Pageable);
}
