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
import org.springframework.web.bind.annotation.*;

import com.smartfixsamana.models.dto.RepairDTO;
import com.smartfixsamana.models.entity.Repair;
import com.smartfixsamana.models.service.RepairService;

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

    @GetMapping("/{id}")
    public Optional<Repair> getById(@PathVariable Long id) {
        return repairService.getById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RepairDTO repairDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validation(bindingResult);
        }
        Repair newRepair = repairService.save(repairDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRepair);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Repair> update(@PathVariable Long id, @RequestBody RepairDTO repairDTO) {
        Repair updateRepair = repairService.update(id, repairDTO);
        return ResponseEntity.ok(updateRepair);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repairService.delete(id);
        return ResponseEntity.ok().body("Eliminado con éxito");
    }

    // Búsqueda paginada por keyword (cliente o celular)
    @GetMapping("/search")
    public ResponseEntity<Page<Repair>> findByKeyword(@RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Repair> results = repairService.findByKeyword(keyword, pageable);
        return ResponseEntity.ok(results);
    }

    private ResponseEntity<?> validation(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
