package com.smartfixsamana.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import com.smartfixsamana.models.dto.PartDTO;
import com.smartfixsamana.models.entity.Phone;
import com.smartfixsamana.models.entity.PartCatalog;
import com.smartfixsamana.models.entity.Part;
import com.smartfixsamana.models.repository.IPartRepository;

@Service
public class PartService {

    @Autowired
    private IPartRepository iPartRepository;

    @Autowired
    private PartCatalogService partCatalogService;

    @Autowired
    private PhoneService phoneService;

    public List<Part> findAll() {
        return (List<Part>) iPartRepository.findAll();
    }

    public Page<Part> findAllPageable(Pageable pageable){
        return this.iPartRepository.findAll(pageable);
    }

    public Optional<Part> findById(@PathVariable Long id) {

        return iPartRepository.findById(id);

    }

    public Part save(PartDTO partDTO) {

        PartCatalog partCatalog = partCatalogService.findById(partDTO.getPartCatalogId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista de repusto no encontrado"));

        Phone phone = phoneService.findById(partDTO.getPhoneId())
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

        PartCatalog partCatalog = partCatalogService.findById(partDTO.getPartCatalogId())
                .orElseThrow(() -> new RuntimeException("Lista de repuesto no encontrada"));
        Phone phone = phoneService.findById(partDTO.getPhoneId())
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

    // Método para buscar por nombre de repuesto
    public List<Part> searchByNamePart(String part) {
        return iPartRepository.findByNameContaining(part);
    }

    // Método para buscar por marca o modelo del celular
    public List<Part> searchByBrandModel(String keyword) {
        return iPartRepository.findByBrandModelContaining(keyword);

    }
}
