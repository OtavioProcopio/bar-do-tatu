package tatu.bar.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tatu.bar.backend.dto.RegisterRequestDTO;
import tatu.bar.backend.dto.UsuarioDTO;
import tatu.bar.backend.entity.Usuario;
import tatu.bar.backend.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario criarUsuario(RegisterRequestDTO registerRequestDTO) {

        Usuario user = new Usuario();
        user.setName(registerRequestDTO.name());
        user.setEmail(registerRequestDTO.email());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.password()));
        return usuarioRepository.save(user);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorNome(String name) {
        Optional<Usuario> usuario = usuarioRepository.findByName(name);
        return usuario.orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    }

    public Usuario buscarPorEmail(String email){
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.orElseThrow(()-> new RuntimeException("Usuario não encontrado"));
    }


    public Usuario buscarPorId(UUID id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    }

    public void deletarUsuario(UUID id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario atualizarUsuario(UUID id, UsuarioDTO usuarioDTO) {
        Usuario usuario = buscarPorId(id);
        usuario.setName(usuarioDTO.getName());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());

        return usuarioRepository.save(usuario);
    }
}
