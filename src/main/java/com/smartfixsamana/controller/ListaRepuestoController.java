package com.smartfixsamana.controller;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.smartfixsamana.models.entity.ListaRepuesto;
import com.smartfixsamana.models.service.ListaRepuestoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/lis_repuestos")
public class ListaRepuestoController {

    @Autowired
    private ListaRepuestoService listaRepuestoService;

    @GetMapping
    public List<ListaRepuesto> findAll() {
        return listaRepuestoService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<ListaRepuesto> findById(@PathVariable Long id) {

        return listaRepuestoService.findById(id);

    }

    @PostMapping
    public ResponseEntity<?> createRepuesto(@Valid @RequestBody ListaRepuesto listaRepuesto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validation(bindingResult);
        }
        ListaRepuesto newListaRepuesto = listaRepuestoService.create(listaRepuesto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newListaRepuesto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateListRepuesto(@Valid @PathVariable Long id,
            @RequestBody ListaRepuesto listaRepuesto, BindingResult bindingResult) {
                if (bindingResult.hasErrors()) {
                    return validation(bindingResult);
                }
        ListaRepuesto updateListRepuesto = listaRepuestoService.update(id, listaRepuesto);
        return ResponseEntity.ok().body(updateListRepuesto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteListRepuesto(@PathVariable Long id) {
        listaRepuestoService.delete(id);
        return ResponseEntity.ok().body("Eliminado con exito");
    }
    
    private ResponseEntity<?> validation(BindingResult bindingResult){
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error ->{
            errors.put(error.getField(), "El campo "+ error.getField() + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
