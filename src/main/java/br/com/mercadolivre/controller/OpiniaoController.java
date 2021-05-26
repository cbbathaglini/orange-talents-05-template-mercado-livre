package br.com.mercadolivre.controller;

import br.com.mercadolivre.dto.request.OpiniaoDTORequest;
import br.com.mercadolivre.model.Opiniao;
import br.com.mercadolivre.model.Produto;
import br.com.mercadolivre.model.Usuario;
import br.com.mercadolivre.repository.OpiniaoRepository;
import br.com.mercadolivre.repository.ProdutoRepository;
import br.com.mercadolivre.validateErrors.ErroAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/opiniao")
public class OpiniaoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private OpiniaoRepository opiniaoRepository;

    @PostMapping(value = "/produtos/{id}")
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
