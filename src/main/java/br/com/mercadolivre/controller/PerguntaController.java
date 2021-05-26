package br.com.mercadolivre.controller;

import br.com.mercadolivre.dto.request.PerguntaDTORequest;
import br.com.mercadolivre.email.Email;
import br.com.mercadolivre.model.Pergunta;
import br.com.mercadolivre.model.Produto;
import br.com.mercadolivre.model.Usuario;
import br.com.mercadolivre.repository.PerguntaRepository;
import br.com.mercadolivre.repository.ProdutoRepository;
import br.com.mercadolivre.repository.UsuarioRepository;
import br.com.mercadolivre.validateErrors.ErroAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/pergunta/produtos")
public class PerguntaController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;


    @PostMapping(value = "/{id}")
    @Transactional
    public ResponseEntity adicionaPergunta(@PathVariable("id") Long idProduto,
                                           @AuthenticationPrincipal Usuario usuarioLogado,
                                           @Valid @RequestBody PerguntaDTORequest perguntaDTORequest){
        Produto produtoEncontrado = Produto.existeProduto(idProduto,produtoRepository);
        if(produtoEncontrado == null){
            return ResponseEntity.status(404).body(new ErroAPI("Produto","O produto não foi encontrado na base de dados."));
        }
        Pergunta pergunta = perguntaDTORequest.converter(usuarioLogado,produtoEncontrado);
        perguntaRepository.save(pergunta);

        Email email = new Email(produtoEncontrado.getVendedor(),perguntaDTORequest.getTitulo(), "Uma pergunta foi realizada no seu produto", perguntaDTORequest.getPergunta());

        return ResponseEntity.ok(email.enviar());
    }
}
