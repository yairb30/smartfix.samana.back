package com.smartfixsamana.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

import com.smartfixsamana.models.dto.RepuestoDTO;
import com.smartfixsamana.models.entity.Repuesto;
import com.smartfixsamana.models.service.RepuestoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/repuestos")
public class RepuestoController {

    @Autowired
    private RepuestoService repuestoService;


    @GetMapping
    public List<Repuesto> findAll() {
        return repuestoService.findAll();
    }
    @GetMapping("/page/{page}")
    public Page<Repuesto> findAllPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 4);
        return this.repuestoService.findAllPageable(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Repuesto> findById(@PathVariable Long id) {

        return repuestoService.findById(id);

    }

    @PostMapping
    public ResponseEntity<?> createRepuesto(@Valid @RequestBody RepuestoDTO repuestoDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return validation(bindingResult);
        }
        Repuesto neewRepuesto = repuestoService.saveRepuesto(repuestoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(neewRepuesto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Repuesto> updateRepuesto(@PathVariable Long id, @RequestBody RepuestoDTO repuestoDTO) {
        Repuesto updaRepuesto = repuestoService.updateRepuesto(id, repuestoDTO);
        return ResponseEntity.ok().body(updaRepuesto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRepuesto(@PathVariable Long id) {
        repuestoService.deleteRepuesto(id);
        return ResponseEntity.ok().body("Eliminado con exito");
    }
    
    // Endpoint para buscar por nombre o detalles del repuesto
    @GetMapping("/search/repuesto")
    public ResponseEntity<List<Repuesto>> searchByNombreRepuesto(@RequestParam String repuesto) {
        List<Repuesto> repuestos = repuestoService.searchByNombreRepuesto(repuesto);
        return ResponseEntity.ok(repuestos);
    }

    // Endpoint para buscar por marca o modelo del celular
    @GetMapping("/search/celular")
    public ResponseEntity<List<Repuesto>> searchByCelularMarcaModelo(@RequestParam String celular) {
        List<Repuesto> repuestos = repuestoService.searchByCelularMarcaModelo(celular);
        return ResponseEntity.ok(repuestos);
    }
     private ResponseEntity<?> validation(BindingResult bindingResult){
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error ->{
            errors.put(error.getField(), "El campo "+ error.getField() + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
