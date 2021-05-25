package br.com.mercadolivre.dto.request;

import br.com.mercadolivre.dto.response.ImagemDoProdutoDTOResponse;
import br.com.mercadolivre.model.ImagemDoProduto;
import br.com.mercadolivre.model.Produto;
import br.com.mercadolivre.upload.UploadImages;
import br.com.mercadolivre.upload.UploaderImage;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ListaImagensDoProdutoDTORequest {

    @Size(min = 1, message = "O produto precisa ter no mínimo uma imagem associada")
    @NotNull
    private List<MultipartFile> imagens = new ArrayList<>();


    public void setImagens(List<MultipartFile> imagens) {
        this.imagens = imagens;
    }


    public Set<ImagemDoProduto> converter(Produto produto, UploadImages uploaderImage){
        return this.imagens.stream().map(i-> {
            try {
                return new ImagemDoProduto(i.getOriginalFilename(),uploaderImage.gerarURL(i),uploaderImage.imgEmBase64(i), produto);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toSet());
    }


    public Set<ImagemDoProdutoDTOResponse> converterToRespose(Set<ImagemDoProduto> setDeImagens) {
        Set<ImagemDoProdutoDTOResponse> imagemDoProdutoDTOResponses = new HashSet<>();
        imagemDoProdutoDTOResponses.addAll(setDeImagens.stream().map(i-> new ImagemDoProdutoDTOResponse(i)).collect(Collectors.toSet()));
        return imagemDoProdutoDTOResponses;
    }
}