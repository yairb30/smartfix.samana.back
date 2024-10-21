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

import com.smartfixsamana.models.dao.ICelularDao;
import com.smartfixsamana.models.entity.Celular;

@Service
public class CelularService {

    @Autowired
    private ICelularDao iCelularDao;

    public List<Celular> findAll() {
        return (List<Celular>) iCelularDao.findAll();
    }

    public Page<Celular> findAll(Pageable pageable) {
        return iCelularDao.findAll(pageable);
    }

    public Optional<Celular> findById(@PathVariable Long id) {

        return iCelularDao.findById(id);

    }

    public Celular createCelular(Celular celular) {

        return iCelularDao.save(celular);

    }

    public Celular updateCelular(Long celularId, Celular celularDetalles) {

        Celular celular = iCelularDao.findById(celularId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Celular no encontrado con el ID: " + celularId));
        celular.setMarca(celularDetalles.getMarca());
        celular.setModelo(celularDetalles.getModelo());

        return iCelularDao.save(celular);
    }

    public void deleteCelular(Long celularId) {

        Celular celular = iCelularDao.findById(celularId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Celular no encontrado con el ID: " + celularId));
        iCelularDao.delete(celular);
    }

    public List<Celular> findByMarca(String marca) {
        return iCelularDao.findByMarca(marca);
    }

    public List<Celular> findByModelo(String modelo) {
        return iCelularDao.findByModelo(modelo);
    }

}
