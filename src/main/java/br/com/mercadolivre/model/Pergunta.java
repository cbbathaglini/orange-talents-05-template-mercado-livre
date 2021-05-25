package br.com.mercadolivre.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String pergunta;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Usuario usuario;

    private LocalDateTime instanteCriacao;

    public Pergunta() {
    }

    public Pergunta(String titulo, String pergunta, Produto produto, Usuario usuario) {
        this.titulo = titulo;
        this.pergunta = pergunta;
        this.produto = produto;
        this.usuario = usuario;
        this.instanteCriacao = LocalDateTime.now();
    }
}
