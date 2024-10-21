package com.smartfixsamana.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.smartfixsamana.models.dao.IReparacionDao;
import com.smartfixsamana.models.dto.ReparacionDTO;
import com.smartfixsamana.models.entity.Celular;
import com.smartfixsamana.models.entity.Cliente;
import com.smartfixsamana.models.entity.Reparacion;

@Service
public class ReparacionService {

    @Autowired
    private IReparacionDao iReparacionDao;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CelularService celularService;

    public List<Reparacion> getAllReparaciones() {
        return (List<Reparacion>) iReparacionDao.findAll();
    }

    public Page<Reparacion> getAllPageable(Pageable pageable) {
        return this.iReparacionDao.findAll(pageable);
    }

    public Optional<Reparacion> getReparacionById(Long id) {
        return iReparacionDao.findById(id);
    }

    public Reparacion saveReparacion(ReparacionDTO reparacionDTO) {
        Cliente cliente = clienteService.findById(reparacionDTO.getClienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        Celular celular = celularService.findById(reparacionDTO.getCelularId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Celular no encontrado"));

        Reparacion reparacion = new Reparacion();

        reparacion.setClienteId(cliente);
        reparacion.setCelularId(celular);
        reparacion.setProblema(reparacionDTO.getProblema());
        reparacion.setEstado(reparacionDTO.getEstado());
        reparacion.setFechaIngreso(reparacionDTO.getFechaIngreso());
        reparacion.setFechaEstimadaEntrega(reparacionDTO.getFechaEstimadaEntrega());

        return iReparacionDao.save(reparacion);
    }

    public Reparacion updateReparacion(Long id, ReparacionDTO reparacionDTO) {
        Reparacion reparacion = iReparacionDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Reparación no encontrada"));

        Cliente cliente = clienteService.findById(reparacionDTO.getClienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        Celular celular = celularService.findById(reparacionDTO.getCelularId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Celular no encontrado"));

        reparacion.setClienteId(cliente);
        reparacion.setCelularId(celular);
        reparacion.setProblema(reparacionDTO.getProblema());
        reparacion.setEstado(reparacionDTO.getEstado());
        reparacion.setFechaIngreso(reparacionDTO.getFechaIngreso());
        reparacion.setFechaEstimadaEntrega(reparacionDTO.getFechaEstimadaEntrega());

        return iReparacionDao.save(reparacion);
    }

    public void deleteReparacion(Long id) {
        Reparacion reparacion = iReparacionDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Reparacion no encontrado con el ID: " + id));

        iReparacionDao.delete(reparacion);
    }

    // Método para buscar por nombre o apellido del cliente
    public List<Reparacion> searchByNombreApellido(String keyword) {
        return iReparacionDao.findByNombreApellidoContaining(keyword);
    }

    // Método para buscar por marca o modelo del celular
    public List<Reparacion> searchByCelularMarcaModelo(String keyword) {
        return iReparacionDao.findByCelularMarcaOrModeloContaining(keyword);

    }

}
