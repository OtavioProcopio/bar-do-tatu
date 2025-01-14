package tatu.bar.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import tatu.bar.backend.dto.ProdutoDTO;
import tatu.bar.backend.entity.Produto;
import tatu.bar.backend.service.ProdutoService;

@RestController
@Tag(name = "Produtos", description = "Restful Api de Produtos")
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/save")
    public ResponseEntity<Produto> criarProduto(@RequestBody ProdutoDTO produtoDTO) {
        Produto produto = produtoService.criarProduto(produtoDTO);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Produto>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    @GetMapping("/findByNameOrCategory")
    public ResponseEntity<List<Produto>> buscarPorNomeOuCategoria(@RequestParam(required = false) String nome,
                                                                   @RequestParam(required = false) String categoria) {
        return ResponseEntity.ok(produtoService.buscarPorNomeOuCategoria(nome, categoria));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.ok(produtoService.atualizarProduto(id, produtoDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}