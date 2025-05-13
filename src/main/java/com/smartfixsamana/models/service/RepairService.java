package com.smartfixsamana.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.smartfixsamana.models.dto.RepairDTO;
import com.smartfixsamana.models.entity.Phone;
import com.smartfixsamana.models.entity.Customer;
import com.smartfixsamana.models.entity.Repair;
import com.smartfixsamana.models.repository.IRepairRepository;

@Service
public class RepairService {

    @Autowired
    private IRepairRepository iRepairRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PhoneService phoneService;

    public List<Repair> getAll() {
        return (List<Repair>) iRepairRepository.findAll();
    }

    public Page<Repair> getAllPageable(Pageable pageable) {
        return this.iRepairRepository.findAll(pageable);
    }

    public Optional<Repair> getById(Long id) {
        return iRepairRepository.findById(id);
    }

    public Repair save(RepairDTO repairDTO) {
        Customer customer = customerService.findById(repairDTO.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        Phone phone = phoneService.findById(repairDTO.getPhoneId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Celular no encontrado"));

        Repair repair = new Repair();

        repair.setCustomer(customer);
        repair.setPhone(phone);
        repair.setFault(repairDTO.getFault());
        repair.setState(repairDTO.getState());
        repair.setDate(repairDTO.getDate());

        return iRepairRepository.save(repair);
    }

    public Repair update(Long id, RepairDTO repairDTO) {
        Repair repair = iRepairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reparación no encontrada"));

        Customer customer = customerService.findById(repairDTO.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        Phone phone = phoneService.findById(repairDTO.getPhoneId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Celular no encontrado"));

        repair.setCustomer(customer);
        repair.setPhone(phone);
        repair.setFault(repairDTO.getFault());
        repair.setState(repairDTO.getState());
        repair.setDate(repairDTO.getDate());

        return iRepairRepository.save(repair);
    }

    public void delete(Long id) {
        Repair repair = iRepairRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Reparacion no encontrado con el ID: " + id));

        iRepairRepository.delete(repair);
    }

    // Método para buscar por nombre o apellido del cliente
    public List<Repair> searchByNameLastname(String keyword) {
        return iRepairRepository.findByNameLastnameContaining(keyword);
    }

    // Método para buscar por marca o modelo del celular
    public List<Repair> searchByBrandModel(String keyword) {
        return iRepairRepository.findByBrandModelContaining(keyword);

    }

}
