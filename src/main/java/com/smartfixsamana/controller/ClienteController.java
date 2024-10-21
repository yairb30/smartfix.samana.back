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

import com.smartfixsamana.models.entity.Cliente;
import com.smartfixsamana.models.service.ClienteService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping

    public List<Cliente> getAllClientes() {

        return clienteService.findAll();
    }

    @GetMapping("/page/{page}")
    public Page<Cliente> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        return clienteService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Cliente> findClienteById(@PathVariable Long id) {

        return clienteService.findById(id);

    }

    @PostMapping()
    public ResponseEntity<?> createCliente(@Valid @RequestBody Cliente cliente, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validation(bindingResult);
        }
        Cliente newCliente = clienteService.createCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@Valid @PathVariable Long id,
            @RequestBody Cliente cliente, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validation(bindingResult);
        }
        Cliente updateCliente = clienteService.updateCliente(id, cliente);
        return ResponseEntity.ok().body(updateCliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id) {

        clienteService.deleteCliente(id);
        return ResponseEntity.ok().body("Cliente eliminado con exito");

    }

    @GetMapping("/nombre")
    public List<Cliente> findByNombre(@RequestParam String nombre) {
        return clienteService.findByNombre(nombre);
    }

    private ResponseEntity<?> validation(BindingResult bindingResult) {

        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);

    }

}
