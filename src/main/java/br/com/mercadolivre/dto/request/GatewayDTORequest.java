package br.com.mercadolivre.dto.request;

import br.com.mercadolivre.model.Compra;
import br.com.mercadolivre.model.GatewayPagamento;
import br.com.mercadolivre.model.Pagamento;
import br.com.mercadolivre.model.StatusPagamento;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;

public class GatewayDTORequest {

    @NotNull
    private Long idTransacao;

    @NotNull
    private String status;

    public GatewayDTORequest() {
    }

    public GatewayDTORequest(Long idTransacao, String status) {
        this.idTransacao = idTransacao;
        this.status = status;
    }


    public Pagamento converter(Compra compraRealizada) {
         return new Pagamento(compraRealizada,compraRealizada.getStatusPagamento(this.status) , this.idTransacao);
    }


    public Long getIdTransacao() {
        return idTransacao;
    }

    public String getStatus() {
        return status;
    }

    public void setIdTransacao(Long idTransacao) {
        this.idTransacao = idTransacao;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
