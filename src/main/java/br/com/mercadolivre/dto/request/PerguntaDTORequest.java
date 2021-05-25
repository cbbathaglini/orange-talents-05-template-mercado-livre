package br.com.mercadolivre.dto.request;

import br.com.mercadolivre.model.Pergunta;
import br.com.mercadolivre.model.Produto;
import br.com.mercadolivre.model.Usuario;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

public class PerguntaDTORequest {

    @NotBlank
    private String titulo;

    @NotBlank
    private String pergunta;

    public PerguntaDTORequest() {
    }

    public PerguntaDTORequest(String titulo, String pergunta) {
        this.titulo = titulo;
        this.pergunta = pergunta;
    }

    public Pergunta converter(Usuario usuario, Produto produto){
        return new Pergunta(this.titulo, this.pergunta, produto, usuario);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }
}
