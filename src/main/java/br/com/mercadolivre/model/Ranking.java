package br.com.mercadolivre.model;

import br.com.mercadolivre.model.Compra;
import br.com.mercadolivre.model.Pagamento;
import br.com.mercadolivre.model.RankingVendedores;
import br.com.mercadolivre.pagamentoListeners.PagamentoComSucessoListener;
import org.springframework.stereotype.Component;

@Component
public class Ranking implements PagamentoComSucessoListener {
    @Override
    public void executa(Pagamento pagamento, Compra compra) {
        RankingVendedores.ranking(compra);
    }
}
