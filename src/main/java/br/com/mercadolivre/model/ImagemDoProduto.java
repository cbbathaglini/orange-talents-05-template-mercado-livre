package br.com.mercadolivre.model;

import org.apache.tomcat.util.codec.binary.Base64;
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

    @NotBlank
    private String link;

    @Column(columnDefinition = "LONGTEXT")
    private String imagemEmBase64;

    @ManyToOne
    private Produto produto;

    public ImagemDoProduto() {
    }

    public ImagemDoProduto(String nome, String link, String imagemEmBase64, Produto produto) {
        this.nome = nome;
        this.link = link;
        this.produto = produto;
        this.imagemEmBase64 = imagemEmBase64;
    }


    public String getNome() {
        return nome;
    }

    public String getLink() {
        return link;
    }
}
