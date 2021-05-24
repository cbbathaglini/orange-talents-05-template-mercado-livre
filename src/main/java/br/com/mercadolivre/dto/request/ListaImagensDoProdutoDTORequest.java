package br.com.mercadolivre.dto.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class ListaImagensDoProdutoDTORequest {


    @NotNull
    private List<ImagemDoProdutoDTORequest> imagens = new ArrayList<>();


    public void setImagens(List<ImagemDoProdutoDTORequest> imagens) {
        this.imagens = imagens;
    }

    public List<ImagemDoProdutoDTORequest> getImagens() {
        return imagens;
    }



}
