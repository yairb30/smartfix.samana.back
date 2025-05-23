package com.smartfixsamana.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.smartfixsamana.models.entity.PartCatalog;
import com.smartfixsamana.models.repository.IPartCatalogRepository;

@Service
public class PartCatalogService {

    @Autowired
    private IPartCatalogRepository IPartCatalogRepository;

    public List<PartCatalog> getAll() {
        return (List<PartCatalog>) IPartCatalogRepository.findAll();
    }

    public Optional<PartCatalog> findById(@PathVariable Long id) {
        return IPartCatalogRepository.findById(id);
    }

}
