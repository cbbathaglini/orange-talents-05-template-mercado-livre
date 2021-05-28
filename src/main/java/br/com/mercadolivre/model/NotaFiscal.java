package br.com.mercadolivre.model;

import io.jsonwebtoken.lang.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class NotaFiscal {

    public static void emitirNotaFiscal(Pagamento pagamento){

        Assert.isTrue(pagamento.sucessoPagamento() , "O pagamento não foi um sucesso");
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = Map.of("idCompra", pagamento.getCompra().getId(),"idComprador", pagamento.getCompra().getComprador().getId());
        restTemplate.postForEntity("http://localhost:8081/notafiscal", request, String.class);
    }
}
