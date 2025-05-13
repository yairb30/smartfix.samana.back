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

import com.smartfixsamana.models.entity.Phone;
import com.smartfixsamana.models.service.PhoneService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/phones")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @GetMapping
    public List<Phone> getPhones() {

        return phoneService.findAll();
    }

    @GetMapping("/page/{page}")
    public Page<Phone> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        return phoneService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Phone> findPhoneById(@PathVariable Long id) {

        return phoneService.findById(id);

    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody Phone phone, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validation(bindingResult);
        }
        Phone newPhone = phoneService.create(phone);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPhone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable Long id, @RequestBody Phone updatePhone,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validation(bindingResult);
        }
        Phone newPhoneUpdate = phoneService.update(id, updatePhone);
        return ResponseEntity.ok(newPhoneUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        phoneService.delete(id);
        return ResponseEntity.ok().body("Celular eliminado con exito");
    }

    @GetMapping("/brand")
    public List<Phone> findByMarca(@RequestParam String brand) {
        return phoneService.findByBrand(brand);
    }

    @GetMapping("/model")
    public List<Phone> findByModelo(@RequestParam String model) {
        return phoneService.findByModel(model);

    }

    private ResponseEntity<?> validation(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        bindingResult.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
