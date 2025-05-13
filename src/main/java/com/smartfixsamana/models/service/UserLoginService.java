package com.smartfixsamana.models.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartfixsamana.models.entity.Role;
import com.smartfixsamana.models.entity.UserLogin;
import com.smartfixsamana.models.repository.IRolRepository;
import com.smartfixsamana.models.repository.IUserLoginRepository;

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

    // Método para guardar un nuevo usuario (registro)
    public UserLogin save(UserLogin user) {
        user.setRoles(getRoles(user)); // Asignar roles
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encriptar contraseña
        return iUserLoginRepository.save(user);
    }

    // Método privado para asignar roles
    private List<Role> getRoles(UserLogin user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> optionalRoleCliente = iRolRepository.findByName("ROLE_CLIENTE");
        optionalRoleCliente.ifPresent(roles::add);

        // Agregar rol de administrador si aplica
        if (user.isAdmin()) {
            Optional<Role> optionalRoleAdmin = iRolRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }
        return roles;
    }
}
