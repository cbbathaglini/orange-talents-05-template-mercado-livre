package br.com.mercadolivre.model;

import br.com.mercadolivre.dto.request.CaracteristicasProdutoDTORequest;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;
    private BigDecimal preco;
    private Integer quantidade;
    private String descricao;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private List<CaracteristicasProduto> caracteristicasProduto;  // um produto pode ter várias características

    @ManyToOne
    private Categoria categoria;
    private LocalDateTime instanteCadastro;

    public Produto() {
    }

    public Produto(String nome, BigDecimal preco, Integer quantidade, String descricao,
                   List<CaracteristicasProdutoDTORequest> caracteristicasProduto,
                   Categoria categoria) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.descricao = descricao;

        List<CaracteristicasProduto> caracteristicasProdutoList = new ArrayList<>();
        for (CaracteristicasProdutoDTORequest c:caracteristicasProduto) {
            caracteristicasProdutoList.add(CaracteristicasProdutoDTORequest.converterLst(c,this));
        }

        this.caracteristicasProduto = caracteristicasProdutoList;
        this.categoria = categoria;
        this.instanteCadastro = LocalDateTime.now();

        Assert.isTrue(this.caracteristicasProduto.size() >= 3,"O produto precisa ter ao menos três características");
        //Assert.isTrue(this.descricao.length() > 1000, "A descrição precisa possuir menos que 1000 caracteres");
        Assert.isTrue((this.preco.compareTo(BigDecimal.ZERO)) == 1, "O preço precisa ser maior que R$ 0,00");
        Assert.isTrue(this.quantidade >= 0, "A quantidade de itens disponíveis precisa ser maior ou igual a zero");
    }
}
