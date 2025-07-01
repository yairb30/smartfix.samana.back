package com.smartfixsamana.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartfixsamana.models.entities.PartCatalog;
import com.smartfixsamana.models.services.PartCatalogService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/parts_catalog")
public class PartCatalogController {

    @Autowired
    private PartCatalogService partCatalogService;

    @GetMapping
    public List<PartCatalog> findAll() {
        return partCatalogService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<PartCatalog> findById(@PathVariable Long id) {

        return partCatalogService.findById(id);

    }

}
