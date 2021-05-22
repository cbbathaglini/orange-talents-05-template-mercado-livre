package br.com.mercadolivre.controller;

import br.com.mercadolivre.dto.request.CategoriaDTORequest;
import br.com.mercadolivre.dto.request.UsuarioDTORequest;
import br.com.mercadolivre.model.Categoria;
import br.com.mercadolivre.model.Usuario;
import br.com.mercadolivre.repository.CategoriaRepository;
import br.com.mercadolivre.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController{

    @Autowired
    private CategoriaRepository categoriaRepository;


    @PostMapping
    @Transactional
    @CacheEvict(value = "listaDeCategorias", allEntries = true)
    public ResponseEntity cadastrar(@RequestBody @Valid CategoriaDTORequest categoriaDTORequest, UriComponentsBuilder uriBuilder){
        Categoria categoria = categoriaDTORequest.converter(categoriaRepository);
        categoriaRepository.save(categoria);
        return ResponseEntity.ok().build();
    }
}
