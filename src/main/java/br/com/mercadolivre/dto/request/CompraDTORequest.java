package br.com.mercadolivre.dto.request;

import br.com.mercadolivre.model.GatewayPagamento;
import br.com.mercadolivre.model.*;
import br.com.mercadolivre.repository.ProdutoRepository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CompraDTORequest {

    @NotNull @Positive
    private Integer quantidade;

    private GatewayPagamento gatewayPagamento;

    public CompraDTORequest() {
    }


    public CompraDTORequest(Integer quantidade, GatewayPagamento gatewayPagamento) {
        this.quantidade = quantidade;
        this.gatewayPagamento = gatewayPagamento;
    }


    //Integer quantidade, Produto produto, Usuario comprador, BigDecimal valorCompra, Status status, GatewayPagamento gatewayPagamento) {
    public Compra converter(Produto produto, Usuario usuario){
        return  new Compra(this.quantidade,produto,usuario,produto.getPreco(),this.gatewayPagamento);
    }



    public Integer getQuantidade() {
        return quantidade;
    }


    public GatewayPagamento getGatewayPagamento() {
        return gatewayPagamento;
    }

}
