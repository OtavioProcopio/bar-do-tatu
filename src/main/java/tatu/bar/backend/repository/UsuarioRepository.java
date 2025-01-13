package tatu.bar.backend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tatu.bar.backend.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository < Usuario, UUID> {

    Optional<List<Usuario>> findByNameContainingIgnoreCase(String nome);
    Optional<List<Usuario>> findByEmailContainingIgnoreCase(String email);

}
