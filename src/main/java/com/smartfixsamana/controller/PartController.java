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

import com.smartfixsamana.models.dto.PartDTO;
import com.smartfixsamana.models.entity.Part;
import com.smartfixsamana.models.service.PartService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/parts")
public class PartController {

    @Autowired
    private PartService partService;


    @GetMapping
    public List<Part> findAll() {
        return partService.findAll();
    }
    @GetMapping("/page/{page}")
    public Page<Part> findAllPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 4);
        return this.partService.findAllPageable(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Part> findById(@PathVariable Long id) {

        return partService.findById(id);

    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PartDTO partDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return validation(bindingResult);
        }
        Part neewPart = partService.save(partDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(neewPart);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Part> update(@PathVariable Long id, @RequestBody PartDTO partDTO) {
        Part updaPart = partService.update(id, partDTO);
        return ResponseEntity.ok().body(updaPart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        partService.delete(id);
        return ResponseEntity.ok().body("Eliminado con exito");
    }
    
    // Endpoint para buscar por nombre o detalles del repuesto
    @GetMapping("/part")
    public ResponseEntity<List<Part>> searchByNamePart(@RequestParam String part) {
        List<Part> parts = partService.searchByNamePart(part);
        return ResponseEntity.ok(parts);
    }

    // Endpoint para buscar por marca o modelo del celular
    @GetMapping("/phone")
    public ResponseEntity<List<Part>> searchByCelularMarcaModelo(@RequestParam String phone) {
        List<Part> parts = partService.searchByBrandModel(phone);
        return ResponseEntity.ok(parts);
    }
     private ResponseEntity<?> validation(BindingResult bindingResult){
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error ->{
            errors.put(error.getField(), "El campo "+ error.getField() + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
