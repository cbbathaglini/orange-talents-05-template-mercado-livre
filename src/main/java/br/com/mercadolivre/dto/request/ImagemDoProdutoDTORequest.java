package br.com.mercadolivre.dto.request;

import br.com.mercadolivre.model.ImagemDoProduto;
import br.com.mercadolivre.model.Produto;
import br.com.mercadolivre.repository.ProdutoRepository;
import br.com.mercadolivre.validates.ExistsId;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

public class ImagemDoProdutoDTORequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String link;

    @NotNull
    //@ExistsId(domainClass = Produto.class, fieldName = "id", message = "O identificador do produto não foi informado")
    private Long idProduto;


    public ImagemDoProdutoDTORequest() {
    }

    public ImagemDoProdutoDTORequest(String nome, String link) {
        this.nome = nome;
        this.link = link;
    }

    public ImagemDoProduto converter(Produto produto){
        return new ImagemDoProduto(this.nome,this.link, produto);
    }

    public static Set<ImagemDoProduto> converter(List<ImagemDoProdutoDTORequest> listaImagensDTO, Produto produto){
        Set<ImagemDoProduto> listaImagens = new HashSet<>();

        for (ImagemDoProdutoDTORequest idto:listaImagensDTO) {
            listaImagens.add(new ImagemDoProduto(idto.getNome(), idto.getLink(), produto));
        }
        return listaImagens;
    }

    public String getNome() {
        return nome;
    }

    public String getLink() {
        return link;
    }

    public Long getIdProduto() {
        return idProduto;
    }


}
