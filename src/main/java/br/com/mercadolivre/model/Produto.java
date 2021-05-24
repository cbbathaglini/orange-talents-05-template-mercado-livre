package br.com.mercadolivre.model;

import br.com.mercadolivre.dto.request.CaracteristicasProdutoDTORequest;
import br.com.mercadolivre.dto.request.ImagemDoProdutoDTORequest;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
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
    private Set<CaracteristicasProduto> caracteristicasProduto = new HashSet<>();  // um produto pode ter várias características

    @ManyToOne
    private Categoria categoria;
    private LocalDateTime instanteCadastro;

    @ManyToOne
    private Usuario vendedor;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    @Size(min = 1)
    private Set<ImagemDoProduto> listaImagens = new HashSet<>();

    public Produto() {
    }

    public Produto(String nome, BigDecimal preco, Integer quantidade, String descricao,
                   Collection<CaracteristicasProdutoDTORequest> caracteristicasProduto,
                   Categoria categoria,Usuario vendedor) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.descricao = descricao;

         this.caracteristicasProduto.addAll(caracteristicasProduto.stream()
                .map(caracteristica -> caracteristica.converter(this))
                .collect(Collectors.toSet()));

        this.categoria = categoria;
        this.instanteCadastro = LocalDateTime.now();
        this.vendedor = vendedor;

        Assert.isTrue(this.caracteristicasProduto.size() >= 3,"O produto precisa ter ao menos três características");
        //Assert.isTrue(this.descricao.length() > 1000, "A descrição precisa possuir menos que 1000 caracteres");
        Assert.isTrue((this.preco.compareTo(BigDecimal.ZERO)) == 1, "O preço precisa ser maior que R$ 0,00");
        Assert.isTrue(this.quantidade >= 0, "A quantidade de itens disponíveis precisa ser maior ou igual a zero");
    }

    public void setListaImagens(Set<ImagemDoProduto> listaImagens) {
        this.listaImagens = listaImagens;
    }
}
