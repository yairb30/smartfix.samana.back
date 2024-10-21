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

import com.smartfixsamana.models.dao.IRepuestoDao;
import com.smartfixsamana.models.dto.RepuestoDTO;
import com.smartfixsamana.models.entity.Celular;
import com.smartfixsamana.models.entity.ListaRepuesto;
import com.smartfixsamana.models.entity.Repuesto;

@Service
public class RepuestoService {

    @Autowired
    private IRepuestoDao iRepuestoDao;

    @Autowired
    private ListaRepuestoService listaRepuestoService;

    @Autowired
    private CelularService celularService;

    public List<Repuesto> findAll() {
        return (List<Repuesto>) iRepuestoDao.findAll();
    }

    public Page<Repuesto> findAllPageable(Pageable pageable){
        return this.iRepuestoDao.findAll(pageable);
    }

    public Optional<Repuesto> findById(@PathVariable Long id) {

        return iRepuestoDao.findById(id);

    }

    public Repuesto saveRepuesto(RepuestoDTO repuestoDTO) {

        ListaRepuesto listaRepuesto = listaRepuestoService.findById(repuestoDTO.getElegirRepuesto())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista de repusto no encontrado"));

        Celular celular = celularService.findById(repuestoDTO.getElegirCelular())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Celular no encontrado"));

        Repuesto repuesto = new Repuesto();

        repuesto.setElegirRepuesto(listaRepuesto);
        repuesto.setElegirCelular(celular);

        return iRepuestoDao.save(repuesto);

    }

    public Repuesto updateRepuesto(Long repuestoId, RepuestoDTO repuestoDTO) {

        Repuesto repuesto = iRepuestoDao.findById(repuestoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Repuesto no encontrado con el ID: " + repuestoId));

        ListaRepuesto listaRepuesto = listaRepuestoService.findById(repuestoDTO.getElegirRepuesto())
                .orElseThrow(() -> new RuntimeException("Lista de repuesto no encontrada"));
        Celular celular = celularService.findById(repuestoDTO.getElegirCelular())
                .orElseThrow(() -> new RuntimeException("Celular no encontrad0"));

        repuesto.setElegirRepuesto(listaRepuesto);
        repuesto.setElegirCelular(celular);

        return iRepuestoDao.save(repuesto);
    }

    public void deleteRepuesto(Long repuestoId) {

        Repuesto repuesto = iRepuestoDao.findById(repuestoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Repuesto no encontrado con el ID: " + repuestoId));
        iRepuestoDao.delete(repuesto);
    }

    // Método para buscar por nombre o detalles de repuesto
    public List<Repuesto> searchByNombreRepuesto(String keyword) {
        return iRepuestoDao.findByNombreRepuestoContaining(keyword);
    }

    // Método para buscar por marca o modelo del celular
    public List<Repuesto> searchByCelularMarcaModelo(String keyword) {
        return iRepuestoDao.findByCelularMarcaOrModeloContaining(keyword);

    }
}
