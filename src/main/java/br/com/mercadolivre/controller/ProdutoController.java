package br.com.mercadolivre.controller;

import br.com.mercadolivre.dto.request.ImagemDoProdutoDTORequest;
import br.com.mercadolivre.dto.request.ListaImagensDoProdutoDTORequest;
import br.com.mercadolivre.dto.request.ProdutoDTORequest;
import br.com.mercadolivre.dto.request.UsuarioDTORequest;
import br.com.mercadolivre.model.ImagemDoProduto;
import br.com.mercadolivre.model.Produto;
import br.com.mercadolivre.model.Usuario;
import br.com.mercadolivre.repository.CategoriaRepository;
import br.com.mercadolivre.repository.ImagemProdutoRepository;
import br.com.mercadolivre.repository.ProdutoRepository;
import br.com.mercadolivre.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    @PostMapping
    @Transactional
    @CacheEvict(value = "listaDeProdutos", allEntries = true)
    public ResponseEntity cadastrar(@RequestBody @Valid ProdutoDTORequest produtoDTO, UriComponentsBuilder uriBuilder){
        Produto produto = produtoDTO.converter(categoriaRepository,usuarioRepository);

        if(produto != null) {
            produtoRepository.save(produto);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping(value = "/{id}/imagens")
    @Transactional
    public ResponseEntity adicionaImagens(@PathVariable("id") Long idProduto,
                                          @NotEmpty(message = "A lista de imagens não pode ser vazia")
                                          @RequestBody List<ImagemDoProdutoDTORequest> listaImagensDTORequest){
        Optional<Usuario> usuarioLogadoOp = usuarioRepository.findByLogin("cbbathaglini2@gmail.com");

        if(Usuario.logado(usuarioLogadoOp)){
            Long idUsuario = usuarioLogadoOp.get().getId();
            Produto produto = produtoRepository.findProdutoByUsuarioId(idUsuario,idProduto);
            if (produto != null) { //verifica se o produto existe neste usuário
                produto.setListaImagens(ImagemDoProdutoDTORequest.converter(listaImagensDTORequest,produto));
                produtoRepository.save(produto);

                return ResponseEntity.ok().build();
            } else{
                return ResponseEntity.status(403).build();
            }
        }

        return ResponseEntity.notFound().build();
    }



}