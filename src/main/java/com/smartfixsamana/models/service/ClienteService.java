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

import com.smartfixsamana.models.dao.IClienteDao;
import com.smartfixsamana.models.entity.Cliente;

@Service
public class ClienteService {

    @Autowired
    private IClienteDao iClienteDao;

    public List<Cliente> findAll() {
        return (List<Cliente>) iClienteDao.findAll();
    }
    

    public Page<Cliente> findAll(Pageable pageable){
        return iClienteDao.findAll(pageable);
    }

    public Optional<Cliente> findById(@PathVariable Long id) {

        return iClienteDao.findById(id);

    }

    public Cliente createCliente(Cliente cliente) {

        return iClienteDao.save(cliente);

    }

    public Cliente updateCliente(Long clienteId, Cliente clienteDetalles) {

        Cliente cliente = iClienteDao.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente no encontrado con el ID: " + clienteId));
        cliente.setNombre(clienteDetalles.getNombre());
        cliente.setApellido(clienteDetalles.getApellido());
        cliente.setTelefono(clienteDetalles.getTelefono());
        cliente.setEmail(clienteDetalles.getEmail());

        return iClienteDao.save(cliente);
    }

    public void deleteCliente(Long clienteId) {

        Cliente cliente = iClienteDao.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente no encontrado con el ID: " + clienteId));
        iClienteDao.delete(cliente);
    }

    public List<Cliente> findByNombre(String nombre) {
        return iClienteDao.findByNombre(nombre);
    }

}
