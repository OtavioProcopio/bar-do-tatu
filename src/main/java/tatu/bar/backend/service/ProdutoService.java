package tatu.bar.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tatu.bar.backend.dto.ProdutoDTO;
import tatu.bar.backend.entity.Produto;
import tatu.bar.backend.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto criarProduto(ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        produto.setNome(produtoDTO.getName());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setCategoria(produtoDTO.getCategoria());
        produto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());
        produto.setPrecoDeCusto(produtoDTO.getPrecoDeCusto());
        produto.setPrecoDeVenda(produtoDTO.getPrecoDeVenda());
        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public List<Produto> buscarPorNomeOuCategoria(String nome, String categoria) {
        if (nome != null && !nome.isEmpty()) {
            return produtoRepository.findByNomeContainingIgnoreCase(nome);
        } else if (categoria != null && !categoria.isEmpty()) {
            return produtoRepository.findByCategoriaContainingIgnoreCase(categoria);
        }
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Produto atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        Produto produto = buscarPorId(id);
        produto.setNome(produtoDTO.getName());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setCategoria(produtoDTO.getCategoria());
        produto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());
        produto.setPrecoDeCusto(produtoDTO.getPrecoDeCusto());
        produto.setPrecoDeVenda(produtoDTO.getPrecoDeVenda());
        return produtoRepository.save(produto);
    }

    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }
}