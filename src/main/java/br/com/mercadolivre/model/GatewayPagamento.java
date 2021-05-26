package br.com.mercadolivre.model;

import br.com.mercadolivre.model.Compra;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


//mesma lógica do curso de SOLID da alura que tinhamos que melhorar o código
public enum GatewayPagamento  {
    PAYPAL{
        String retornoURL(Compra compra, UriComponentsBuilder uriBuilder) {
            URI uri = uriBuilder.path("/compra-ret-pagseguro/{id}").buildAndExpand(compra.getId()).toUri();
            return "paypal.com?buyerId=" + compra.getId() + "&redirectUrl=" + uri;
        }


    },PAGSEGURO{
        String retornoURL(Compra compra,UriComponentsBuilder uriBuilder) {
            URI uri = uriBuilder.path("/compra-ret-paypal/{id}").buildAndExpand(compra.getId()).toUri();
            return "pagseguro.com?returnId=" + compra.getId() + "?redirectUrl=" + uri;
        }
    };

    abstract String retornoURL(Compra compra, UriComponentsBuilder uriComponentsBuilder);
}
