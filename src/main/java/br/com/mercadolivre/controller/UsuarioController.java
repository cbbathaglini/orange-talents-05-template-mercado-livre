package br.com.mercadolivre.controller;

import br.com.mercadolivre.dto.request.UsuarioDTORequest;
import br.com.mercadolivre.model.Usuario;
import br.com.mercadolivre.repository.UsuarioRepository;
import br.com.mercadolivre.validates.ValidaEmailDuplicadoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /*@Autowired
    private ValidaEmailDuplicadoUsuario validaEmailDuplicadoUsuario;

    @InitBinder
    public void init(WebDataBinder binder){
        binder.addValidators(validaEmailDuplicadoUsuario);
    }
     */

    @PostMapping
    @Transactional
    @CacheEvict(value = "listaDeUsuarios", allEntries = true)
    public ResponseEntity cadastrar(@RequestBody @Valid UsuarioDTORequest usuarioDTO, UriComponentsBuilder uriBuilder){
        Usuario usuario = usuarioDTO.converter();
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().build();
    }
}
