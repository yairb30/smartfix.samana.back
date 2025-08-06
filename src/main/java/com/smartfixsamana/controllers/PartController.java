package com.smartfixsamana.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.smartfixsamana.models.entities.Part;
import com.smartfixsamana.models.services.PartService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/parts")
public class PartController {

    private final PartService partService;

    public PartController(PartService partService) {
        this.partService = partService;
    }
    @GetMapping("/count")
    public Long countParts() {
        return partService.countAll();
    }


    @GetMapping
    public List<Part> findAll() {
        return partService.findAll();
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
    
     @GetMapping("/search")
    public ResponseEntity<Page<Part>> findByKeyword(@RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Part> results = partService.findByKeyword(keyword, pageable);
        return ResponseEntity.ok(results);
    }
   
     private ResponseEntity<?> validation(BindingResult bindingResult){
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), "El campo "+ error.getField() + error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

}
