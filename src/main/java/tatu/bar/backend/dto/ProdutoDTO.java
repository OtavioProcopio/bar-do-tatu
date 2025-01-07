package tatu.bar.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ProdutoDTO {

    private String name;

    private String descricao;

    private String categoria;

    private int quantidadeEstoque;

    private double precoDeCusto;

    private double precoDeVenda;

}
