package tatu.bar.backend.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import tatu.bar.backend.dto.ProdutoDTO;
import tatu.bar.backend.entity.Produto;
import tatu.bar.backend.service.ProdutoService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@Disabled
@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProdutoService produtoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testCriarProduto() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO("Produto Teste", "Descrição Teste", "Categoria Teste", 10, 50.0, 70.0);
        Produto produto = new Produto(1L, "Produto Teste", "Descrição Teste", "Categoria Teste", 10, 50.0, 70.0);

        when(produtoService.criarProduto(any(ProdutoDTO.class))).thenReturn(produto);

        mockMvc.perform(post("/api/v1/produtos/save")
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

        mockMvc.perform(get("/api/v1/produtos/listAll")
                .with(user("testUser"))) // Simula o usuário autenticado
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Produto 1"))
                .andExpect(jsonPath("$[1].nome").value("Produto 2"));
    }

    @Test
    public void testBuscarProdutoPorId() throws Exception {
        Produto produto = new Produto(1L, "Produto Teste", "Descrição Teste", "Categoria Teste", 10, 50.0, 70.0);

        when(produtoService.buscarPorId(1L)).thenReturn(produto);

        mockMvc.perform(get("/api/v1/produtos/findById/1")
                .with(user("testUser"))) // Simula o usuário autenticado
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Teste"));
    }

    @Test
    public void testAtualizarProduto() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO("Produto Atualizado", "Descrição Atualizada", "Categoria Atualizada", 15, 60.0, 80.0);
        Produto produtoAtualizado = new Produto(1L, "Produto Atualizado", "Descrição Atualizada", "Categoria Atualizada", 15, 60.0, 80.0);

        when(produtoService.atualizarProduto(eq(1L), any(ProdutoDTO.class))).thenReturn(produtoAtualizado);

        mockMvc.perform(put("/api/v1/produtos/atualizar/1")
                .with(user("testUser")) // Simula o usuário autenticado
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Atualizado"));
    }

    @Test
    public void testDeletarProduto() throws Exception {
        mockMvc.perform(delete("/api/v1/produtos/delete/1")
                .with(user("testUser"))) // Simula o usuário autenticado
                .andExpect(status().isNoContent());
    }
}
