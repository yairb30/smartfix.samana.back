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

import com.smartfixsamana.models.entity.Customer;
import com.smartfixsamana.models.repository.ICustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private ICustomerRepository iCustomerRepository;

    public List<Customer> findAll() {
        return (List<Customer>) iCustomerRepository.findAll();
    }
    

    public Page<Customer> findAll(Pageable pageable){
        return iCustomerRepository.findAll(pageable);
    }

    public Optional<Customer> findById(@PathVariable Long id) {

        return iCustomerRepository.findById(id);

    }

    public Customer create(Customer customer) {

        return iCustomerRepository.save(customer);

    }

    public Customer update(Long phoneId, Customer customerDetails) {

        Customer cliente = iCustomerRepository.findById(phoneId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente no encontrado con el ID: " + phoneId));
        cliente.setName(customerDetails.getName());
        cliente.setLastname(customerDetails.getLastname());
        cliente.setPhone(customerDetails.getPhone());
        cliente.setEmail(customerDetails.getEmail());

        return iCustomerRepository.save(cliente);
    }

    public void delete(Long customerId) {

        Customer customer = iCustomerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente no encontrado con el ID: " + customerId));
        iCustomerRepository.delete(customer);
    }

    public List<Customer> findByName(String name) {
       return iCustomerRepository.findByName(name);
    }

}
