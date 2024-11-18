package com.smartfixsamana.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.smartfixsamana.models.dao.IListaRepuestoDao;
import com.smartfixsamana.models.entity.ListaRepuesto;

@Service
public class ListaRepuestoService {

    @Autowired
    private IListaRepuestoDao iListaRepuestoDao;

    public List<ListaRepuesto> getAll() {
        return (List<ListaRepuesto>) iListaRepuestoDao.findAll();
    }

    public Optional<ListaRepuesto> findById(Long id) {
        return iListaRepuestoDao.findById(id);
    }

    public ListaRepuesto create(ListaRepuesto nombre) {
        return iListaRepuestoDao.save(nombre);
    }

    public ListaRepuesto update(Long id, ListaRepuesto actualizar) {
        ListaRepuesto listaRepuesto = iListaRepuestoDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Repuesto no encontrado con el ID: " + id));
        listaRepuesto.setNombre(actualizar.getNombre());
        listaRepuesto.setDetalles(actualizar.getDetalles());
        return iListaRepuestoDao.save(listaRepuesto);

    }

    public void delete(Long id) {
        ListaRepuesto listaRepuesto = iListaRepuestoDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Repuesto no encontrado con el ID: " + id));
        iListaRepuestoDao.delete(listaRepuesto);
    }

}
