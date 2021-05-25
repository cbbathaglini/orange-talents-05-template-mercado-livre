package br.com.mercadolivre.dto.request;

import br.com.mercadolivre.model.ImagemDoProduto;
import br.com.mercadolivre.model.Produto;
import br.com.mercadolivre.repository.ProdutoRepository;
import br.com.mercadolivre.validates.ExistsId;
import org.springframework.web.multipart.MultipartFile;

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

    private String imagemEmBase64;

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
        return new ImagemDoProduto(this.nome,this.link, this.link,produto);
    }

    public static Set<ImagemDoProduto> converter(List<ImagemDoProdutoDTORequest> listaImagensDTO, Produto produto){
        Set<ImagemDoProduto> listaImagens = new HashSet<>();

        for (ImagemDoProdutoDTORequest idto:listaImagensDTO) {
            listaImagens.add(new ImagemDoProduto(idto.getNome(), idto.getLink(), idto.getImagemEmBase64(),produto));
        }
        return listaImagens;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLink() {
        return link;
    }

    public void setImagem(String link) {
        this.link = link;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImagemEmBase64() {
        return imagemEmBase64;
    }

    public void setImagemEmBase64(String imagemEmBase64) {
        this.imagemEmBase64 = imagemEmBase64;
    }
}
