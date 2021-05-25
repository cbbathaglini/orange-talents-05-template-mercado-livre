package br.com.mercadolivre.controller;

import br.com.mercadolivre.dto.request.*;
import br.com.mercadolivre.dto.response.ImagemDoProdutoDTOResponse;
import br.com.mercadolivre.model.ImagemDoProduto;
import br.com.mercadolivre.model.Opiniao;
import br.com.mercadolivre.model.Produto;
import br.com.mercadolivre.model.Usuario;
import br.com.mercadolivre.repository.*;
import br.com.mercadolivre.upload.UploadImages;
import br.com.mercadolivre.upload.UploaderImage;
import br.com.mercadolivre.validateErrors.ErroAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ImagemProdutoRepository imagemProdutoRepository;

    @Autowired
    private OpiniaoRepository opiniaoRepository;

    @PostMapping
    @Transactional
    @CacheEvict(value = "listaDeProdutos", allEntries = true)
    public ResponseEntity cadastrar(@RequestBody @Valid ProdutoDTORequest produtoDTO, @AuthenticationPrincipal Usuario usuarioLogado, UriComponentsBuilder uriBuilder){

        Produto produto = produtoDTO.converter(categoriaRepository,usuarioLogado);
        if (produto != null) {
            produtoRepository.save(produto);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }


    @PostMapping(value = "/{id}/imagens")
    @Transactional
    public ResponseEntity adicionaImagens(@PathVariable("id") Long idProduto,
                                          @AuthenticationPrincipal Usuario usuarioLogado,
                                          @Valid ListaImagensDoProdutoDTORequest listaImagensDoProdutoDTORequest,
                                          @Autowired UploadImages  uploaderImage){
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



    @PostMapping(value = "/{id}/opiniao")
    @Transactional
    public ResponseEntity adicionaOpiniao(@PathVariable("id") Long idProduto,
                                          @AuthenticationPrincipal Usuario usuarioLogado,
                                          @Valid @RequestBody OpiniaoDTORequest opiniaoDTORequest){
        Produto produtoEncontrado = Produto.existeProduto(idProduto,produtoRepository);
        if(produtoEncontrado == null){
            return ResponseEntity.status(404).body(new ErroAPI("Produto","O produto não foi encontrado na base de dados."));
        }
        Opiniao opiniao = opiniaoDTORequest.converter(usuarioLogado,produtoEncontrado);
        opiniaoRepository.save(opiniao);
        return ResponseEntity.ok().build();
    }



}