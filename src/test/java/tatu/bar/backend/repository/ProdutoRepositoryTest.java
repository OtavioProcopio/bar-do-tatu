package tatu.bar.backend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import tatu.bar.backend.entity.Produto;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    public void testSalvarProduto() {
        Produto produto = new Produto();
        produto.setNome("Cerveja");
        produto.setDescricao("Bebida Alcoólica");
        produto.setCategoria("Bebidas");
        produto.setQuantidadeEstoque(10);
        produto.setPrecoDeCusto(5.00);
        produto.setPrecoDeVenda(10.00);

        Produto produtoSalvo = produtoRepository.save(produto);
        assertNotNull(produtoSalvo.getId());
        assertEquals("Cerveja", produtoSalvo.getNome());
    }

    @Test
    public void testBuscarPorCategoria() {
        Produto produto = new Produto();
        produto.setNome("Cerveja");
        produto.setDescricao("Bebida Alcoólica");
        produto.setCategoria("Bebidas");
        produto.setQuantidadeEstoque(10);
        produto.setPrecoDeCusto(5.00);
        produto.setPrecoDeVenda(10.00);
        produtoRepository.save(produto);

        List<Produto> produtos = produtoRepository.findByCategoriaContainingIgnoreCase("Bebidas");
        assertFalse(produtos.isEmpty());
        assertEquals("Bebidas", produtos.get(0).getCategoria());
    }
}

