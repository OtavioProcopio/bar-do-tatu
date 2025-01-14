package tatu.bar.backend.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tatu.bar.backend.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository < Usuario, UUID> {

    Optional<Usuario> findByName(String nome);
    Optional<Usuario> findByEmail(String email);

}
