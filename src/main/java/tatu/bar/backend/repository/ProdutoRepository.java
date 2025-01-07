package tatu.bar.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import tatu.bar.backend.entity.Produto;

public interface ProdutoRepository extends JpaRepository <Produto, Long> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findByCategoriaContainingIgnoreCase(String categoria);
}
