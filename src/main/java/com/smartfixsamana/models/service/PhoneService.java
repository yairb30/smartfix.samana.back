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

import com.smartfixsamana.models.entity.Phone;
import com.smartfixsamana.models.repository.IPhoneRepository;

@Service
public class PhoneService {

    @Autowired
    private IPhoneRepository iPhoneRepository;

    public List<Phone> findAll() {
        return (List<Phone>) iPhoneRepository.findAll();
    }

    public Page<Phone> findAll(Pageable pageable) {
        return iPhoneRepository.findAll(pageable);
    }

    public Optional<Phone> findById(@PathVariable Long id) {

        return iPhoneRepository.findById(id);

    }

    public Phone create(Phone phone) {

        return iPhoneRepository.save(phone);

    }

    public Phone update(Long phoneId, Phone phoneDetails) {

        Phone phone = iPhoneRepository.findById(phoneId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Celular no encontrado con el ID: " + phoneId));
        phone.setBrand(phone.getBrand());
        phone.setModel(phone.getModel());

        return iPhoneRepository.save(phone);
    }

    public void delete(Long phoneId) {

        Phone phone = iPhoneRepository.findById(phoneId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Celular no encontrado con el ID: " + phoneId));
        iPhoneRepository.delete(phone);
    }

    public List<Phone> findByBrand(String brand) {
        return iPhoneRepository.findByBrand(brand);
    }

    public List<Phone> findByModel(String model) {
        return iPhoneRepository.findByModel(model);
    }

}
