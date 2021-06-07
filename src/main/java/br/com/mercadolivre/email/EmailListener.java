package br.com.mercadolivre.email;

import br.com.mercadolivre.pagamentoListeners.PagamentoComSucessoListener;
import br.com.mercadolivre.model.Compra;
import br.com.mercadolivre.model.Pagamento;

public class EmailListener implements PagamentoComSucessoListener {

    @Override
    public void executa(Pagamento pagamento, Compra compra) {
        Email email = new Email(compra.getComprador(),null, "Pedido realizado", compra.getProduto().montarInfos());
        email.enviar();
    }
}
