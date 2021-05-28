package br.com.mercadolivre.model;

import br.com.mercadolivre.dto.request.GatewayDTORequest;
import br.com.mercadolivre.model.Compra;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


//mesma lógica do curso de SOLID da alura que tinhamos que melhorar o código
public enum GatewayPagamento  {
    PAYPAL{
        @Override
        String retornoURL(Compra compra, UriComponentsBuilder uriBuilder) {
            URI uri = uriBuilder.path("/compra-retorno/paypal/{id}").buildAndExpand(compra.getId()).toUri();
            return "paypal.com?buyerId=" + compra.getId() + "&redirectUrl=" + uri;
        };

        StatusPagamento retornaStatus(String statusPassado) {
            if(statusPassado.equals("1")) return StatusPagamento.Sucesso;
            else return StatusPagamento.Falha;
        };


    },PAGSEGURO{
        @Override
        String retornoURL(Compra compra,UriComponentsBuilder uriBuilder) {
            URI uri = uriBuilder.path("/compra-retorno/pagseguro/{id}").buildAndExpand(compra.getId()).toUri();
            return "pagseguro.com?returnId=" + compra.getId() + "?redirectUrl=" + uri;
        };

         StatusPagamento retornaStatus(String statusPassado) {
             if(statusPassado.equals("SUCESSO")) return StatusPagamento.Sucesso;
             else return StatusPagamento.Falha;
        };
    };

    abstract String retornoURL(Compra compra, UriComponentsBuilder uriComponentsBuilder);
    abstract StatusPagamento retornaStatus(String statusPassado);

}
