package com.smartfixsamana.models.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smartfixsamana.models.dao.IUsuarioDao;
import com.smartfixsamana.models.entity.Usuario;

@Service
public class UserJpaDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioDao iUsuarioDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usurioOptional = iUsuarioDao.findByUsername(username);

        if (usurioOptional.isEmpty()) {

            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema", username));

        }
        Usuario usuario = usurioOptional.orElseThrow();

        List<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

        return new User(username, usuario.getPassword(), true, true, true, true, authorities);
    }
}
