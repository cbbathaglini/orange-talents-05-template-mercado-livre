package br.com.mercadolivre.controller;


import br.com.mercadolivre.dto.request.GatewayDTORequest;
import br.com.mercadolivre.email.Email;
import br.com.mercadolivre.model.*;
import br.com.mercadolivre.pagamentoListeners.PagamentoComFalhaListener;
import br.com.mercadolivre.pagamentoListeners.PagamentoComSucessoListener;
import br.com.mercadolivre.repository.CompraRepository;
import br.com.mercadolivre.repository.PagamentoRepository;
import br.com.mercadolivre.validateErrors.ErroAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/compra-retorno")
public class RetornosGatewaysController {
    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private Collection<PagamentoComSucessoListener> pagamentoComSucessoListenerCollection;

    @Autowired
    private Collection<PagamentoComFalhaListener> pagamentoComFalhaListeners;

    private Email email;

    @PostMapping(value = "/pagseguro/{id}")
    @Transactional
    public ResponseEntity pagamentoPagseguro(@PathVariable("id") Long idCompra,
                                            @Valid @RequestBody GatewayDTORequest pagseguroRequest,
                                             UriComponentsBuilder uriComponentsBuilder){
        //verifico se a compra existe
        Optional<Compra> compraOptional = compraRepository.findById(idCompra);
        if(!compraOptional.isPresent()){
            return ResponseEntity.status(404).body(new ErroAPI("Compra","A compra não foi encontrada na base de dados."));
        }
        return processarPagamento(compraOptional.get(),pagseguroRequest,uriComponentsBuilder);
    }


    @PostMapping(value = "/paypal/{id}")
    public ResponseEntity pagamentoPaypal(@PathVariable("id") Long idCompra,
                                          @Valid @RequestBody GatewayDTORequest paypalRequest,
                                          UriComponentsBuilder uriComponentsBuilder){
        //verifico se a compra existe
        Optional<Compra> compraOptional = compraRepository.findById(idCompra);
        if(!compraOptional.isPresent()){
            return ResponseEntity.status(404).body(new ErroAPI("Compra","A compra não foi encontrada na base de dados."));
        }
        return processarPagamento(compraOptional.get(),paypalRequest,uriComponentsBuilder);
    }


    public ResponseEntity processarPagamento(Compra compraRealizada,GatewayDTORequest gatewayDTORequest,UriComponentsBuilder uriComponentsBuilder) {

        if(compraRealizada.getStatusPagamento(gatewayDTORequest.getStatus())) {
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
            Pagamento pagamento = compraRealizada.adicionarPagamento(compraRepository,gatewayDTORequest.converter(compraRealizada));

            //emissao nota fiscal
            //ranking vendedores
            //envio email
            pagamentoComSucessoListenerCollection.stream().forEach(listener -> listener.executa(pagamento,compraRealizada));

        }else{
            Pagamento pagamento = compraRealizada.adicionarPagamento(compraRepository,gatewayDTORequest.converter(compraRealizada));
            System.out.println("Falha no pagamento...");
            //envio email
            pagamentoComFalhaListeners.stream().forEach(listener -> listener.executa(compraRealizada,uriComponentsBuilder));
        }

        return ResponseEntity.ok().body( email.enviar());
    }

}
