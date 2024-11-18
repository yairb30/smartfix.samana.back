package com.smartfixsamana.models.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.smartfixsamana.models.dao.IRolDao;
import com.smartfixsamana.models.dao.IUsuarioDao;
import com.smartfixsamana.models.entity.Role;
import com.smartfixsamana.models.entity.Usuario;

@Service
public class UsuarioService {

    private  IUsuarioDao iUsuarioDao;
    private  IRolDao iRolDao;
    private  PasswordEncoder passwordEncoder;

    public UsuarioService(IUsuarioDao iUsuarioDao, IRolDao iRolDao, PasswordEncoder passwordEncoder) {
        this.iUsuarioDao = iUsuarioDao;
        this.iRolDao = iRolDao;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para guardar un nuevo usuario (registro)
    public Usuario saveUsuario(Usuario usuario) {
        usuario.setRoles(getRoles(usuario)); // Asignar roles
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Encriptar contraseña
        return iUsuarioDao.save(usuario);
    }

    // Método privado para asignar roles
    private List<Role> getRoles(Usuario usuario) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> optionalRoleCliente = iRolDao.findByName("ROLE_CLIENTE");
        optionalRoleCliente.ifPresent(roles::add);

        // Agregar rol de administrador si aplica
        if (usuario.isAdmin()) {
            Optional<Role> optionalRoleAdmin = iRolDao.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }
        return roles;
    }
}
