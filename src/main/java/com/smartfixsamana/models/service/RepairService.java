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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reparación no encontrada"));

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
                        "Reparación no encontrada con el ID: " + id));
        iRepairRepository.delete(repair);
    }

    // Búsqueda paginada por palabra clave
    public Page<Repair> findByKeyword(String keyword, Pageable pageable) {
        return iRepairRepository.findRepairsByKeyword(keyword, pageable);
    }
}
