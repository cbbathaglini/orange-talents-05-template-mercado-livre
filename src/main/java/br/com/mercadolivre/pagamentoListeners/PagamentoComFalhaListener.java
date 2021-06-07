package br.com.mercadolivre.pagamentoListeners;

import br.com.mercadolivre.model.Compra;
import br.com.mercadolivre.model.Pagamento;
import org.springframework.web.util.UriComponentsBuilder;

public interface PagamentoComFalhaListener {
    void executa(Compra compra, UriComponentsBuilder uriComponentsBuilder);
}