package br.com.mercadolivre.dto.response;

import br.com.mercadolivre.dto.request.CategoriaDTORequest;
import br.com.mercadolivre.model.Categoria;
import org.springframework.data.domain.Page;

public class CategoriaDTOResponse {

    private Long id;
    private String nome;
    private Categoria categoria;

    public CategoriaDTOResponse() {
    }

    public CategoriaDTOResponse(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.categoria = categoria.getCategoriaMae();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public static Page<CategoriaDTOResponse> converterLista(Page<Categoria> listaCategorias ){
        return listaCategorias.map(CategoriaDTOResponse::new);
    }
}
