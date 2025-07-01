package com.smartfixsamana.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.smartfixsamana.models.entities.UserLogin;
import com.smartfixsamana.models.services.UserLoginService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/userslogin")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @GetMapping
    public List<UserLogin> findAll() {
        return userLoginService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserLogin user, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userLoginService.save(user));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UserLogin user, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<UserLogin> userOptional = userLoginService.update(user, id);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userLoginService.delete(id);
        return ResponseEntity.ok().body("Eliminado con exito");
    }


    private ResponseEntity<?> validation(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        bindingResult.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
