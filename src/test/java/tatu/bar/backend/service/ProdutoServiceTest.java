package tatu.bar.backend.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import tatu.bar.backend.dto.ProdutoDTO;
import tatu.bar.backend.entity.Produto;
import tatu.bar.backend.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    public ProdutoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarProduto() {
        ProdutoDTO produtoDTO = new ProdutoDTO("Produto 1", "Descrição", "Categoria", 10, 100.0, 150.0);
        Produto produto = new Produto(1L, "Produto 1", "Descrição", "Categoria", 10, 100.0, 150.0);

        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto criado = produtoService.criarProduto(produtoDTO);

        assertNotNull(criado);
        assertEquals(produto.getNome(), criado.getNome());
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    public void testListarProdutos() {
        Produto produto1 = new Produto(1L, "Produto 1", "Descrição 1", "Categoria 1", 10, 100.0, 150.0);
        Produto produto2 = new Produto(2L, "Produto 2", "Descrição 2", "Categoria 2", 20, 200.0, 250.0);

        when(produtoRepository.findAll()).thenReturn(List.of(produto1, produto2));

        List<Produto> produtos = produtoService.listarProdutos();

        assertNotNull(produtos);
        assertEquals(2, produtos.size());
        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarPorId() {
        Produto produto = new Produto(1L, "Produto 1", "Descrição", "Categoria", 10, 100.0, 150.0);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Produto encontrado = produtoService.buscarPorId(1L);

        assertNotNull(encontrado);
        assertEquals("Produto 1", encontrado.getNome());
        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    public void testAtualizarProduto() {
        Produto produtoExistente = new Produto(1L, "Produto Antigo", "Descrição", "Categoria", 10, 100.0, 150.0);
        ProdutoDTO produtoDTO = new ProdutoDTO("Produto Atualizado", "Nova Descrição", "Nova Categoria", 20, 150.0, 200.0);
        Produto produtoAtualizado = new Produto(1L, "Produto Atualizado", "Nova Descrição", "Nova Categoria", 20, 150.0, 200.0);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoExistente));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoAtualizado);

        Produto atualizado = produtoService.atualizarProduto(1L, produtoDTO);

        assertNotNull(atualizado);
        assertEquals("Produto Atualizado", atualizado.getNome());
        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    public void testDeletarProduto() {
        doNothing().when(produtoRepository).deleteById(1L);

        produtoService.deletarProduto(1L);

        verify(produtoRepository, times(1)).deleteById(1L);
    }
}
