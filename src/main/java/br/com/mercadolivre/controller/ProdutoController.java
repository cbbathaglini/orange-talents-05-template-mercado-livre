package br.com.mercadolivre.controller;

import br.com.mercadolivre.dto.request.ProdutoDTORequest;
import br.com.mercadolivre.dto.request.UsuarioDTORequest;
import br.com.mercadolivre.model.Produto;
import br.com.mercadolivre.model.Usuario;
import br.com.mercadolivre.repository.CategoriaRepository;
import br.com.mercadolivre.repository.ProdutoRepository;
import br.com.mercadolivre.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    @Transactional
    @CacheEvict(value = "listaDeProdutos", allEntries = true)
    public ResponseEntity cadastrar(@RequestBody @Valid ProdutoDTORequest produtoDTO, UriComponentsBuilder uriBuilder){
        Produto produto = produtoDTO.converter(categoriaRepository);
        if(produto != null) {
            produtoRepository.save(produto);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}