package br.com.mercadolivre.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Opiniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TINYINT")
    @Min(value = 1) @Max(value = 5)
    private Integer nota;

    @NotBlank
    private String titulo;

    @Column(columnDefinition = "LONGTEXT")
    @Length(max = 500)
    private String descricao;

    @NotNull
    @ManyToOne
    private Usuario usuario;

    @NotNull
    @ManyToOne
    private Produto produto;

    public Opiniao() {
    }

    public Opiniao(String titulo, String descricao, Usuario usuario, Produto produto) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.usuario = usuario;
        this.produto = produto;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Integer getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}
