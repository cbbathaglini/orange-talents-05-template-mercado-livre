package br.com.mercadolivre.controller;


import br.com.mercadolivre.dto.request.NotaFiscalDTORequest;
import br.com.mercadolivre.model.Compra;
import br.com.mercadolivre.repository.CompraRepository;
import br.com.mercadolivre.validateErrors.ErroAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/notafiscal")
public class EmitirNotaFiscalController {

    @Autowired
    private CompraRepository compraRepository;

    @PostMapping
    public void emitirNotaFiscal( @Valid @RequestBody NotaFiscalDTORequest notaFiscalRequest) throws InterruptedException {

        Compra compraRealizada = Compra.existeCompra(notaFiscalRequest.getIdCompra(),compraRepository);
        if(compraRealizada != null){
            BigDecimal temp = new BigDecimal(compraRealizada.getQuantidade());

            BigDecimal resultado = compraRealizada.getProduto().getPreco().multiply(temp);

            System.out.println("NOTA FISCAL \n\n" +
                    " Código da Compra:  " + compraRealizada.getId() + "\n" +
                    " Preço total da Compra:  " + resultado + "\n" +
                    " Produto :  " +compraRealizada.getProduto().montarInfos());
            Thread.sleep(150);
            return;
        }
        System.out.println("Erro ao emitir a nota fiscal");
        Thread.sleep(150);

    }
}
