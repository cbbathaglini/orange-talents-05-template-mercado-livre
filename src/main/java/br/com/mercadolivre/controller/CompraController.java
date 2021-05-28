package br.com.mercadolivre.controller;

import br.com.mercadolivre.dto.request.CompraDTORequest;
import br.com.mercadolivre.email.Email;
import br.com.mercadolivre.model.*;
import br.com.mercadolivre.repository.CompraRepository;
import br.com.mercadolivre.repository.ProdutoRepository;
import br.com.mercadolivre.validateErrors.ErroAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/compra/produtos")
public class CompraController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CompraRepository compraRepository;

    @PostMapping(value = "/{id}")
    public ResponseEntity compraProduto(@PathVariable("id") Long idProduto,
                                        @AuthenticationPrincipal Usuario usuarioLogado,
                                        @Valid @RequestBody CompraDTORequest compraDTORequest,
                                        UriComponentsBuilder uriComponentsBuilder){

        Produto produtoEncontrado = Produto.existeProduto(idProduto,produtoRepository);
        if(produtoEncontrado == null){
            return ResponseEntity.status(404).body(new ErroAPI("Produto","O produto não foi encontrado na base de dados."));
        }

        if(!produtoEncontrado.verificaEAbateQuantidade(produtoRepository)){
            return ResponseEntity.status(400).body(new ErroAPI("Produto","O produto não tem estoque disponível."));
        }

        Compra compra = compraDTORequest.converter(produtoEncontrado,usuarioLogado);
        compraRepository.save(compra);

        Email email = new Email(produtoEncontrado.getVendedor(),null, "Compra de produto","O usuário "+usuarioLogado.getUsername()+" se interessou pela compra do produto "+produtoEncontrado.getNome());
        email.enviar();

        return ResponseEntity.status(302).body(Compra.URLRetorno(compra,uriComponentsBuilder));
    }
}
