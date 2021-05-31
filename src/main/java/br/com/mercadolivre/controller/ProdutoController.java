package br.com.mercadolivre.controller;

import br.com.mercadolivre.dto.request.*;
import br.com.mercadolivre.dto.response.DetalhesProdutoDTOResponse;
import br.com.mercadolivre.dto.response.ImagemDoProdutoDTOResponse;
import br.com.mercadolivre.email.Email;
import br.com.mercadolivre.model.*;
import br.com.mercadolivre.repository.*;
import br.com.mercadolivre.upload.UploadImages;
import br.com.mercadolivre.upload.UploaderImage;
import br.com.mercadolivre.validateErrors.ErroAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @PostMapping
    @Transactional
    @CacheEvict(value = "listaDeProdutos", allEntries = true)
    public ResponseEntity cadastrar(@RequestBody @Valid ProdutoDTORequest produtoDTO,
                                    @AuthenticationPrincipal Usuario usuarioLogado){

        Produto produto = produtoDTO.converter(categoriaRepository,usuarioLogado);
        if (produto != null) {
            produtoRepository.save(produto);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }


}