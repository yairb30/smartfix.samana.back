package com.smartfixsamana.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartfixsamana.models.dto.ReparacionDTO;
import com.smartfixsamana.models.entity.Reparacion;
import com.smartfixsamana.models.service.ReparacionService;

import jakarta.persistence.EntityNotFoundException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/reparaciones")
public class ReparacionController {

    @Autowired
    private ReparacionService reparacionService;

    @GetMapping
    public List<Reparacion> findAll() {

        return reparacionService.getAllReparaciones();
    }
    @GetMapping("/page/{page}")
    public Page<Reparacion> findAllPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 4);
        return this.reparacionService.getAllPageable(pageable);
    }

    @GetMapping("/{id}")
    public Reparacion getById(@PathVariable Long id) {

        return reparacionService.getReparacionById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reparación no encontrada con el id: " + id));

    }

    @PostMapping
    public ResponseEntity<Reparacion> createReparacion(@RequestBody ReparacionDTO reparacionDTO) {
        Reparacion nuevaReparacion = reparacionService.saveReparacion(reparacionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReparacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reparacion> updateReparacion(@PathVariable Long id,
            @RequestBody ReparacionDTO reparacionDTO) {
        Reparacion reparacionActualizada = reparacionService.updateReparacion(id, reparacionDTO);
        return ResponseEntity.ok(reparacionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReparacion(@PathVariable Long id) {
        reparacionService.deleteReparacion(id);
        return ResponseEntity.ok().body("Eliminado con éxito");
    }
       // Endpoint para buscar por nombre o detalles del repuesto
    @GetMapping("/search/cliente")
    public ResponseEntity<List<Reparacion>> searchByNombreApellido(@RequestParam("keyword") String keyword) {
        List<Reparacion> clientes = reparacionService.searchByNombreApellido(keyword);
        return ResponseEntity.ok(clientes);
    }

    // Endpoint para buscar por marca o modelo del celular
    @GetMapping("/search/celular")
    public ResponseEntity<List<Reparacion>> searchByCelularMarcaModelo(@RequestParam("keyword") String keyword) {
        List<Reparacion> celulares = reparacionService.searchByCelularMarcaModelo(keyword);
        return ResponseEntity.ok(celulares);
    }

}
