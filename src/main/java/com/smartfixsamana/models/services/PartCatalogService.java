package com.smartfixsamana.models.services;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.smartfixsamana.models.entities.PartCatalog;
import com.smartfixsamana.models.repositories.IPartCatalogRepository;

@Service
public class PartCatalogService {

    private final IPartCatalogRepository IPartCatalogRepository;

    public PartCatalogService(IPartCatalogRepository IPartCatalogRepository) {
        this.IPartCatalogRepository = IPartCatalogRepository;
    }

    public List<PartCatalog> getAll() {
        return (List<PartCatalog>) IPartCatalogRepository.findAll();
    }

    public Optional<PartCatalog> findById(@PathVariable Long id) {
        return IPartCatalogRepository.findById(id);
    }

}
