package br.com.mercadolivre.dto.response;

import br.com.mercadolivre.model.Pergunta;

import javax.validation.constraints.NotBlank;

public class PerguntaDTOResponse {

    private String titulo;
    private String pergunta;

    public PerguntaDTOResponse(String titulo, String pergunta) {
        this.titulo = titulo;
        this.pergunta = pergunta;
    }

    public PerguntaDTOResponse(Pergunta pergunta) {
        this.titulo = pergunta.getTitulo();
        this.pergunta = pergunta.getPergunta();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getPergunta() {
        return pergunta;
    }
}
