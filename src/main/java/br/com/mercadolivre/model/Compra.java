package br.com.mercadolivre.model;

import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    @ManyToOne
    private Produto produto; // um produto pode ter várias compras

    private BigDecimal valorProduto;

    @ManyToOne
    private Usuario comprador;//um comprador pode ter várias compras

    private Status status;

    private GatewayPagamento gatewayPagamento;

    public Compra() {
    }

    public Compra(Integer quantidade, Produto produto, Usuario comprador, BigDecimal valorProduto, GatewayPagamento gatewayPagamento) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.comprador = comprador;
        this.valorProduto = valorProduto;
        this.status = Status.INICIADA;
        this.gatewayPagamento = gatewayPagamento;
    }

    public static String URLRetorno(Compra compra, UriComponentsBuilder uriComponentsBuilder) {
        return compra.getGatewayPagamento().retornoURL(compra, uriComponentsBuilder);
    }

    public Long getId() {
        return id;
    }

    public GatewayPagamento getGatewayPagamento() {
        return gatewayPagamento;
    }


}
