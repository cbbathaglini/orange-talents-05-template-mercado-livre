package br.com.mercadolivre.model;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class ImagemDoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank @URL
    private String link;

    @ManyToOne

    private Produto produto;

    public ImagemDoProduto() {
    }

    public ImagemDoProduto(String nome, String link, Produto produto) {
        this.nome = nome;
        this.link = link;
        this.produto = produto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImagemDoProduto that = (ImagemDoProduto) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(link, that.link) && Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, link, produto);
    }
}
