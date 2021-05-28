package br.com.mercadolivre.dto.request;

import br.com.mercadolivre.model.Compra;
import br.com.mercadolivre.repository.CompraRepository;

public class RankingVendedoresDTORequest {

    private Long idCompra;
    private Long idVendedor;


    public RankingVendedoresDTORequest(Long idCompra, Long idVendedor) {
        this.idCompra = idCompra;
        this.idVendedor = idVendedor;
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public Long getIdVendedor() {
        return idVendedor;
    }

}
