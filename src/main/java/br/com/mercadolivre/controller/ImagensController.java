package br.com.mercadolivre.controller;

import br.com.mercadolivre.dto.request.ListaImagensDoProdutoDTORequest;
import br.com.mercadolivre.model.ImagemDoProduto;
import br.com.mercadolivre.model.Produto;
import br.com.mercadolivre.model.Usuario;
import br.com.mercadolivre.repository.ProdutoRepository;
import br.com.mercadolivre.upload.UploadImages;
import br.com.mercadolivre.validateErrors.ErroAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/imagens")
public class ImagensController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping(value = "/produtos/{id}")
    @Transactional
    public ResponseEntity adicionaImagens(@PathVariable("id") Long idProduto,
                                          @AuthenticationPrincipal Usuario usuarioLogado,
                                          @Valid ListaImagensDoProdutoDTORequest listaImagensDoProdutoDTORequest,
                                          @Autowired UploadImages uploaderImage){

        Produto produtoEncontrado = Produto.existeProduto(idProduto,produtoRepository);
        if( produtoEncontrado == null){
            return ResponseEntity.status(404).body(new ErroAPI("Produto","O produto não foi encontrado na base de dados."));
        }

        Produto produto = produtoRepository.findProdutoByUsuarioId(usuarioLogado.getId(),idProduto);
        if (produto != null) { //verifica se o produto existe neste usuário
            Set<ImagemDoProduto> setDeImagens = listaImagensDoProdutoDTORequest.converter(produto,uploaderImage);
            produto.setListaImagens(setDeImagens);
            produtoRepository.save(produto); //merge com as novas infos, com as imagens
            return ResponseEntity.ok(listaImagensDoProdutoDTORequest.converterToRespose(setDeImagens));
        }

        return ResponseEntity.status(403).body(new ErroAPI("Produto","O produto não pertence a este vendedor"));
    }
}
