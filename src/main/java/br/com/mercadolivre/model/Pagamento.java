package br.com.mercadolivre.model;

import br.com.mercadolivre.repository.PagamentoRepository;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Compra compra;

    @NotNull
    private Long idTransacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;//sucesso ou falha

    @NotNull
    private LocalDateTime instantePagamento;



    public Pagamento() {
    }

    public Pagamento(Compra compra, StatusPagamento statusPagamento,Long idTransacao) {
        this.compra = compra;
        this.statusPagamento = statusPagamento;
        this.idTransacao = idTransacao;
        this.instantePagamento = LocalDateTime.now();
    }

    public static boolean procurarTransacao(Long idTransacao, PagamentoRepository pagamentoRepository) {
        List<Pagamento> pagamentos = pagamentoRepository.findByIdTransacao(idTransacao);
        int sucesso = 0;
        for (Pagamento p: pagamentos) {
            if(p.getStatusPagamento() == StatusPagamento.Sucesso) {sucesso++;}
            if (sucesso >= 1) return true;
        }

        return false;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public boolean sucessoPagamento(){
        return this.getStatusPagamento().equals(StatusPagamento.Sucesso);
    }

    public Compra getCompra() {
        return compra;
    }
}
