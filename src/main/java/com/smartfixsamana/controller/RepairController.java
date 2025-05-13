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

import com.smartfixsamana.models.dto.RepairDTO;
import com.smartfixsamana.models.entity.Repair;
import com.smartfixsamana.models.service.RepairService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/repairs")
public class RepairController {

    @Autowired
    private RepairService repairService;

    @GetMapping
    public List<Repair> findAll() {

        return repairService.getAll();
    }
    @GetMapping("/page/{page}")
    public Page<Repair> findAllPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 4);
        return this.repairService.getAllPageable(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Repair> getById(@PathVariable Long id) {

        return repairService.getById(id);
    }

    @PostMapping
    public ResponseEntity<?> createReparacion(@Valid @RequestBody RepairDTO repairDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validation(bindingResult);
        }
        Repair newRepair = repairService.save(repairDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRepair);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Repair> update(@PathVariable Long id,
            @RequestBody RepairDTO repairDTO) {
        Repair updateRepair = repairService.update(id, repairDTO);
        return ResponseEntity.ok(updateRepair);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repairService.delete(id);
        return ResponseEntity.ok().body("Eliminado con éxito");
    }
       // Endpoint para buscar por nombre del cliente
    @GetMapping("/customer")
    public ResponseEntity<List<Repair>> searchByNanme(@RequestParam String customer) {
        List<Repair> customers = repairService.searchByNameLastname(customer);
        return ResponseEntity.ok(customers);
    }

    // Endpoint para buscar por marca del celular
    @GetMapping("/phone")
    public ResponseEntity<List<Repair>> searchByCelularMarcaModelo(@RequestParam String phone) {
        List<Repair> phones = repairService.searchByBrandModel(phone);
        return ResponseEntity.ok(phones);
    }
     private ResponseEntity<?> validation(BindingResult bindingResult){
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error ->{
            errors.put(error.getField(), "El campo "+ error.getField() + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
    

}
