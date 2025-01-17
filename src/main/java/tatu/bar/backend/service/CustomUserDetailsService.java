package tatu.bar.backend.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import tatu.bar.backend.entity.Usuario;
import tatu.bar.backend.repository.UsuarioRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = this.usuarioRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Username not Found"));
    
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
    
}
