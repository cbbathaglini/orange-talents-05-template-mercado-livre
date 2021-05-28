package br.com.mercadolivre.model;

import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class RankingVendedores {

    public static void ranking(Compra compra){
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = Map.of("idCompra", compra.getId(),"idVendedor", compra.getProduto().getVendedor().getId());
        restTemplate.postForEntity("http://localhost:8081/ranking-vendedores", request, String.class);
    }
}
