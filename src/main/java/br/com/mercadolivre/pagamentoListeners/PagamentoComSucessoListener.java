package br.com.mercadolivre.pagamentoListeners;

import br.com.mercadolivre.model.Compra;
import br.com.mercadolivre.model.Pagamento;

public interface PagamentoComSucessoListener {
    void executa(Pagamento pagamento, Compra compra);
}
