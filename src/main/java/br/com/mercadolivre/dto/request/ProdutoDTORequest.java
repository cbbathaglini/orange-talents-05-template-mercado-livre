package br.com.mercadolivre.dto.request;

import br.com.mercadolivre.model.*;
import br.com.mercadolivre.repository.CategoriaRepository;
import br.com.mercadolivre.repository.ProdutoRepository;
import br.com.mercadolivre.repository.UsuarioRepository;
import br.com.mercadolivre.validates.ExistsId;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoDTORequest {
    @NotBlank
    private String nome;
    @NotNull @Positive
    private BigDecimal preco;

    @NotNull @PositiveOrZero
    private Integer quantidade;

    @NotBlank @Length(max = 1000)
    private String descricao;

    @Size(min = 3)
    private List<CaracteristicasProdutoDTORequest> caracteristicasProdutoDTORequestList  = new ArrayList<>(); // um produto pode ter várias características

    @NotNull
    @ExistsId(domainClass = Categoria.class, fieldName = "id")
    private Long idCategoria;




    public ProdutoDTORequest() {
    }

    public List<CaracteristicasProdutoDTORequest> getCaracteristicas() {
        return caracteristicasProdutoDTORequestList;
    }

    public void setCaracteristicas(List<CaracteristicasProdutoDTORequest> caracteristicas) {
        this.caracteristicasProdutoDTORequestList = caracteristicas;
    }

    public ProdutoDTORequest(String nome, BigDecimal preco, Integer quantidade,
                             String descricao, List<CaracteristicasProdutoDTORequest>
                                     caracteristicasProdutoDTORequestList, Long idCategoria) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.caracteristicasProdutoDTORequestList.addAll(caracteristicasProdutoDTORequestList);
        this.idCategoria = idCategoria;

    }

    public Produto converter(CategoriaRepository categoriaRepository, Usuario usuario) {
        Optional<Categoria> categoriaOp = categoriaRepository.findById(this.idCategoria);// acha a categoria a partir do id

        if(categoriaOp.isPresent()) {
            return new Produto(nome, preco, quantidade, descricao, caracteristicasProdutoDTORequestList, categoriaOp.get(),usuario);
        }

        return  null;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<CaracteristicasProdutoDTORequest> getCaracteristicasProdutoDTORequestList() {
        return caracteristicasProdutoDTORequestList;
    }

    public void setCaracteristicasProdutoDTORequestList(List<CaracteristicasProdutoDTORequest> caracteristicasProdutoDTORequestList) {
        this.caracteristicasProdutoDTORequestList = caracteristicasProdutoDTORequestList;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return "ProdutoDTORequest{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                ", caracteristicasProdutoDTORequestList=" + caracteristicasProdutoDTORequestList +
                ", idCategoria=" + idCategoria +
                '}';
    }
}
