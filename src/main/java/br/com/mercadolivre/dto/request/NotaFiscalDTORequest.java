package br.com.mercadolivre.dto.request;

import br.com.mercadolivre.model.Compra;
import br.com.mercadolivre.repository.CompraRepository;

public class NotaFiscalDTORequest {
    private Long idCompra;
    private Long idComprador;


    public NotaFiscalDTORequest(Long idCompra, Long idComprador) {
        this.idCompra = idCompra;
        this.idComprador = idComprador;
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public Long getIdComprador() {
        return idComprador;
    }

}
