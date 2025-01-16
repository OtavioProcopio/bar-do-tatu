package tatu.bar.backend.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import tatu.bar.backend.dto.ProdutoDTO;
import tatu.bar.backend.entity.Produto;
import tatu.bar.backend.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto criarProdutoComImagem(ProdutoDTO produtoDTO, MultipartFile imagem) {
        String caminhoImagem = null;
        if (imagem != null && !imagem.isEmpty()) {
            caminhoImagem = salvarImagem(imagem);
        }

        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setCategoria(produtoDTO.getCategoria());
        produto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());
        produto.setPrecoDeCusto(produtoDTO.getPrecoDeCusto());
        produto.setPrecoDeVenda(produtoDTO.getPrecoDeVenda());
        produto.setCaminhoImagem(caminhoImagem);
        return produtoRepository.save(produto);
    }

    public Produto atualizarProdutoComImagem(Long id, ProdutoDTO produtoDTO, MultipartFile imagem) {
        Produto produto = buscarPorId(id);

        String caminhoImagem = produto.getCaminhoImagem();
        if (imagem != null && !imagem.isEmpty()) {
            caminhoImagem = salvarImagem(imagem);
        }

        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setCategoria(produtoDTO.getCategoria());
        produto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());
        produto.setPrecoDeCusto(produtoDTO.getPrecoDeCusto());
        produto.setPrecoDeVenda(produtoDTO.getPrecoDeVenda());
        produto.setCaminhoImagem(caminhoImagem);
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

    public void deletarProduto(Long id) {
        Produto produto = buscarPorId(id);
        if (produto.getCaminhoImagem() != null) {
            deletarImagem(produto.getCaminhoImagem());
        }
        produtoRepository.deleteById(id);
    }

    private String salvarImagem(MultipartFile imagem) {
        try {
            String nomeArquivo = System.currentTimeMillis() + "-" + imagem.getOriginalFilename();
            Path caminho = Paths.get(UPLOAD_DIR + nomeArquivo);
            Files.createDirectories(caminho.getParent());
            Files.write(caminho, imagem.getBytes());
            return "/images/" + nomeArquivo;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a imagem", e);
        }
    }

    private void deletarImagem(String caminhoRelativo) {
        try {
            Path caminho = Paths.get("src/main/resources/static" + caminhoRelativo);
            Files.deleteIfExists(caminho);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deletar a imagem", e);
        }
    }

    public ProdutoDTO convertJsonToProdutoDTO(String produtoJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(produtoJson, ProdutoDTO.class);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao converter JSON para ProdutoDTO", e);
        }
    }
}
