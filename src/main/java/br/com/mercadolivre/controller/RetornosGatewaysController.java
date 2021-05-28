package br.com.mercadolivre.controller;


import br.com.mercadolivre.dto.request.GatewayDTORequest;
import br.com.mercadolivre.email.Email;
import br.com.mercadolivre.model.*;
import br.com.mercadolivre.repository.CompraRepository;
import br.com.mercadolivre.repository.PagamentoRepository;
import br.com.mercadolivre.repository.ProdutoRepository;
import br.com.mercadolivre.validateErrors.ErroAPI;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/compra-retorno")
public class RetornosGatewaysController {
    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private CompraRepository compraRepository;

    private Email email;

    @PostMapping(value = "/pagseguro/{id}")
    @Transactional
    public ResponseEntity pagamentoPagseguro(@PathVariable("id") Long idCompra,
                                            @Valid @RequestBody GatewayDTORequest pagseguroRequest,
                                             UriComponentsBuilder uriComponentsBuilder){
        //verifico se a compra existe
        Compra compraRealizada = Compra.existeCompra(idCompra,compraRepository);
        if(compraRealizada == null){
            return ResponseEntity.status(404).body(new ErroAPI("Compra","A compra não foi encontrada na base de dados."));
        }
        return processarPagamento(compraRealizada,pagseguroRequest,uriComponentsBuilder);
    }


    @PostMapping(value = "/paypal/{id}")
    public ResponseEntity pagamentoPaypal(@PathVariable("id") Long idCompra,
                                          @Valid @RequestBody GatewayDTORequest paypalRequest,
                                          UriComponentsBuilder uriComponentsBuilder){
        //verifico se a compra existe
        Compra compraRealizada = Compra.existeCompra(idCompra,compraRepository);
        if(compraRealizada == null){
            return ResponseEntity.status(404).body(new ErroAPI("Compra","A compra não foi encontrada na base de dados."));
        }
        return processarPagamento(compraRealizada,paypalRequest,uriComponentsBuilder);
    }


    public ResponseEntity processarPagamento(Compra compraRealizada,GatewayDTORequest gatewayDTORequest,UriComponentsBuilder uriComponentsBuilder) {
        if(compraRealizada.getStatusPagamento(gatewayDTORequest.getStatus()).equals(StatusPagamento.Sucesso)) {
            System.out.println("Sucesso no pagamento...");

            //O id de uma transação que vem de alguma plataforma de pagamento só pode ser processado com sucesso uma vez.
            //Uma transação que foi concluída com sucesso não pode ter seu status alterado para qualquer coisa outra coisa.
            if(Pagamento.procurarTransacao(gatewayDTORequest.getIdTransacao(), pagamentoRepository)){
                return ResponseEntity.status(400).body(new ErroAPI("Transação","Já existe um pagamento com sucesso para esta transação."));
            }

            //Uma compra não pode ter mais de duas transações concluídas com sucesso associada a ela.
            if(compraRealizada.pagamentoInvalido(compraRepository)){
                return ResponseEntity.status(400).body(new ErroAPI("Compra","A compra já possui mais de duas transações concluídas com sucesso."));
            }

            //novo pagamento
            Pagamento pagamento = gatewayDTORequest.converter(compraRealizada);
            pagamento = compraRealizada.adicionarPagamento(compraRepository,pagamento);

            //emissao nota fiscal
            NotaFiscal.emitirNotaFiscal(pagamento);

            //ranking vendedores
            RankingVendedores.ranking(compraRealizada);

            //envio email
            email = new Email(compraRealizada.getComprador(),null, "Pedido realizado", compraRealizada.getProduto().montarInfos());
            email.enviar();


        }else{
            //envio email
            Pagamento pagamento = gatewayDTORequest.converter(compraRealizada);
            pagamento = compraRealizada.adicionarPagamento(compraRepository,pagamento);
            System.out.println("Falha no pagamento...");

            email = new Email(compraRealizada.getComprador(),null, "Pedido falhou", "Realize o pagamento novamente através do link: " + Compra.URLRetorno(compraRealizada, uriComponentsBuilder));

        }

        return ResponseEntity.ok().body( email.enviar());
    }

}
