package tatu.bar.backend.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import tatu.bar.backend.dto.ProdutoDTO;
import tatu.bar.backend.entity.Produto;
import tatu.bar.backend.service.ProdutoService;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProdutoService produtoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCriarProduto() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO("Produto Teste", "Descrição Teste", "Categoria Teste", 10, 50.0, 70.0);
        Produto produto = new Produto(1L, "Produto Teste", "Descrição Teste", "Categoria Teste", 10, 50.0, 70.0);

        when(produtoService.criarProduto(any(ProdutoDTO.class))).thenReturn(produto);

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Teste"));
    }

    @Test
    public void testListarProdutos() throws Exception {
        Produto produto1 = new Produto(1L, "Produto 1", "Descrição 1", "Categoria 1", 10, 20.0, 30.0);
        Produto produto2 = new Produto(2L, "Produto 2", "Descrição 2", "Categoria 2", 5, 25.0, 35.0);

        when(produtoService.listarProdutos()).thenReturn(Arrays.asList(produto1, produto2));

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Produto 1"))
                .andExpect(jsonPath("$[1].nome").value("Produto 2"));
    }

    @Test
    public void testBuscarProdutoPorId() throws Exception {
        Produto produto = new Produto(1L, "Produto Teste", "Descrição Teste", "Categoria Teste", 10, 50.0, 70.0);

        when(produtoService.buscarPorId(1L)).thenReturn(produto);

        mockMvc.perform(get("/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Teste"));
    }

    @Test
    public void testAtualizarProduto() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO("Produto Atualizado", "Descrição Atualizada", "Categoria Atualizada", 15, 60.0, 80.0);
        Produto produtoAtualizado = new Produto(1L, "Produto Atualizado", "Descrição Atualizada", "Categoria Atualizada", 15, 60.0, 80.0);

        when(produtoService.atualizarProduto(eq(1L), any(ProdutoDTO.class))).thenReturn(produtoAtualizado);

        mockMvc.perform(put("/produtos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Atualizado"));
    }

    @Test
    public void testDeletarProduto() throws Exception {
        mockMvc.perform(delete("/produtos/1"))
                .andExpect(status().isNoContent());
    }
}
