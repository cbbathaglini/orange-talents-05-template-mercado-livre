package br.com.mercadolivre.model;

import br.com.mercadolivre.model.Compra;
import br.com.mercadolivre.model.NotaFiscal;
import br.com.mercadolivre.model.Pagamento;
import br.com.mercadolivre.pagamentoListeners.PagamentoComSucessoListener;
import org.springframework.stereotype.Component;

@Component
public class EmissorNotaFiscal implements PagamentoComSucessoListener {
    @Override
    public void executa(Pagamento pagamento, Compra compra) {
        NotaFiscal.emitirNotaFiscal(pagamento);
    }
}
