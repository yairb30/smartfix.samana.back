package com.smartfixsamana.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartfixsamana.models.entities.PartCatalog;
import com.smartfixsamana.models.services.PartCatalogService;


@RestController
@RequestMapping("/parts_catalog")
public class PartCatalogController {

    private final PartCatalogService partCatalogService;

    public PartCatalogController(PartCatalogService partCatalogService) {
        this.partCatalogService = partCatalogService;
    }

    @GetMapping
    public List<PartCatalog> findAll() {
        return partCatalogService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<PartCatalog> findById(@PathVariable Long id) {

        return partCatalogService.findById(id);

    }

}
