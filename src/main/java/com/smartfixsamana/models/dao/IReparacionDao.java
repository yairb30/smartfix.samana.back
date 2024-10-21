package com.smartfixsamana.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.smartfixsamana.models.entity.Reparacion;

public interface IReparacionDao extends CrudRepository<Reparacion, Long> {

    @Query("SELECT r FROM Reparacion r WHERE r.clienteId.nombre LIKE %:keyword% OR r.clienteId.apellido LIKE %:keyword%")
    List<Reparacion> findByNombreApellidoContaining(@Param("keyword") String keyword);

    @Query("SELECT r FROM Reparacion r WHERE r.celularId.marca LIKE %:keyword% OR r.celularId.modelo LIKE %:keyword%")
    List<Reparacion> findByCelularMarcaOrModeloContaining(@Param("keyword") String keyword);

    Page<Reparacion> findAll(Pageable pageable);

}
