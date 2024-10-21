package com.smartfixsamana.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
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

import com.smartfixsamana.models.entity.Celular;
import com.smartfixsamana.models.service.CelularService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/celulares")
public class CelularController {

    @Autowired
    private CelularService celularService;

    @GetMapping
    public List<Celular> getAllCelulars() {

        return celularService.findAll();
    }

    @GetMapping("/page/{page}")
    public Page<Celular> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        return celularService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Celular> findCelularById(@PathVariable Long id) {

        return celularService.findById(id);

    }

    @PostMapping()
    public ResponseEntity<?> createCelular(@Valid @RequestBody Celular celular, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validation(bindingResult);
        }
        Celular newCelular = (Celular) celularService.createCelular(celular);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCelular);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCelular(@Valid @PathVariable Long id, @RequestBody Celular updateCelular,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validation(bindingResult);
        }
        Celular newUpdateCelular = celularService.updateCelular(id, updateCelular);
        return ResponseEntity.ok(newUpdateCelular);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delteCelular(@PathVariable Long id) {
        celularService.deleteCelular(id);
        return ResponseEntity.ok().body("Celular eliminado con exito");
    }

    @GetMapping("/marca")
    public List<Celular> findByMarca(@RequestParam String marca) {
        return celularService.findByMarca(marca);
    }

    @GetMapping("/modelo")
    public List<Celular> findByModelo(@RequestParam String modelo) {
        return celularService.findByModelo(modelo);

    }

    private ResponseEntity<?> validation(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        bindingResult.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
