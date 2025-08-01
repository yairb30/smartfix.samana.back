package com.smartfixsamana.models.services;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import com.smartfixsamana.models.dto.PartDTO;
import com.smartfixsamana.models.entities.Phone;
import com.smartfixsamana.models.entities.PartCatalog;
import com.smartfixsamana.models.entities.Part;
import com.smartfixsamana.models.repositories.IPartRepository;

@Service
public class PartService {

    private final IPartRepository iPartRepository;

    private final PartCatalogService partCatalogService;

    private final PhoneService phoneService;

    public PartService(IPartRepository iPartRepository, PartCatalogService partCatalogService, PhoneService phoneService) {
        this.iPartRepository = iPartRepository;
        this.partCatalogService = partCatalogService;
        this.phoneService = phoneService;
    }

    public Long countAll() {
        return iPartRepository.count();
    }

    public List<Part> findAll() {
        return (List<Part>) iPartRepository.findAll();
    }

    public Optional<Part> findById(@PathVariable Long id) {

        return iPartRepository.findById(id);

    }

    public Part save(PartDTO partDTO) {

        PartCatalog partCatalog = partCatalogService.findById(partDTO.partCatalogId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista de repusto no encontrado"));

        Phone phone = phoneService.findById(partDTO.phoneId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Celular no encontrado"));

        Part part = new Part();

        part.setPartCatalog(partCatalog);
        part.setPhone(phone);

        return iPartRepository.save(part);

    }

    public Part update(Long partId, PartDTO partDTO) {

        Part part = iPartRepository.findById(partId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Repuesto no encontrado con el ID: " + partId));

        PartCatalog partCatalog = partCatalogService.findById(partDTO.partCatalogId())
                .orElseThrow(() -> new RuntimeException("Lista de repuesto no encontrada"));
        Phone phone = phoneService.findById(partDTO.phoneId())
                .orElseThrow(() -> new RuntimeException("Celular no encontrad0"));

        part.setPartCatalog(partCatalog);
        part.setPhone(phone);

        return iPartRepository.save(part);
    }

    public void delete(Long partId) {

        Part part = iPartRepository.findById(partId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Repuesto no encontrado con el ID: " + partId));
        iPartRepository.delete(part);
    }

    public Page<Part> findByKeyword(String keyword, Pageable pageable) {
        return iPartRepository.findPartsByKeyword(keyword, pageable);
    }
}
