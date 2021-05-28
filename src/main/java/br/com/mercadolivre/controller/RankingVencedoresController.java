package br.com.mercadolivre.controller;


import br.com.mercadolivre.dto.request.RankingVendedoresDTORequest;
import br.com.mercadolivre.model.Compra;
import br.com.mercadolivre.repository.CompraRepository;
import br.com.mercadolivre.validateErrors.ErroAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/ranking-vendedores")
public class RankingVencedoresController {

    @Autowired
    private CompraRepository compraRepository;

    @PostMapping
    public void rankingVendedores(@Valid @RequestBody RankingVendedoresDTORequest rankingVendedoresDTORequest) throws InterruptedException {

        Compra compraRealizada = Compra.existeCompra(rankingVendedoresDTORequest.getIdCompra(),compraRepository);
        if(compraRealizada != null){
            System.out.println("Ranking dos vendedores \n ");
            Thread.sleep(150);
            return;
        }
        System.out.println("Erro ao exibir ranking dos vendedores");
        Thread.sleep(150);

    }
}
