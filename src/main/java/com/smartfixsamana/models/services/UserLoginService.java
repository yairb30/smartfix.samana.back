package com.smartfixsamana.models.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartfixsamana.models.entities.Role;
import com.smartfixsamana.models.entities.UserLogin;
import com.smartfixsamana.models.repositories.IRolRepository;
import com.smartfixsamana.models.repositories.IUserLoginRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserLoginService {

    private  IUserLoginRepository iUserLoginRepository;
    private  IRolRepository iRolRepository;
    private  PasswordEncoder passwordEncoder;

    public UserLoginService(IUserLoginRepository iUserLoginRepository, IRolRepository iRolRepository, PasswordEncoder passwordEncoder) {
        this.iUserLoginRepository = iUserLoginRepository;
        this.iRolRepository = iRolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserLogin> findAll() {
        return ((List<UserLogin>) this.iUserLoginRepository.findAll()).stream().map(user -> {

            boolean admin = user.getRoles().stream().anyMatch(role -> "ROLE_ADMIN".equals(role.getName()));
            user.setAdmin(admin);
            return user;
        }).collect(Collectors.toList());
    }

    // Método para guardar un nuevo usuario (registro)
    public UserLogin save(UserLogin user) {
        user.setRoles(getRoles(user)); // Asignar roles
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encriptar contraseña
        return iUserLoginRepository.save(user);
    }

    public Optional<UserLogin> update(UserLogin user, Long id) {
        Optional<UserLogin> userOptional = iUserLoginRepository.findById(id);

        if (userOptional.isPresent()) {
            UserLogin userDb = userOptional.get();
            userDb.setEmail(user.getEmail());
            userDb.setUsername(user.getUsername());
            userDb.setRoles(getRoles(user));

            return Optional.of(iUserLoginRepository.save(userDb));
        }
        return Optional.empty();
    }

    public void delete(Long id) {
        iUserLoginRepository.deleteById(id);
    }

    // Método privado para asignar roles
    private List<Role> getRoles(UserLogin user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> optionalRoleCliente = iRolRepository.findByName("ROLE_USER");
        optionalRoleCliente.ifPresent(roles::add);

        // Agregar rol de administrador si aplica
        if (user.isAdmin()) {
            Optional<Role> optionalRoleAdmin = iRolRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }
        return roles;
    }
}
