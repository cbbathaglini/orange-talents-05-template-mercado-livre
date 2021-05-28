package br.com.mercadolivre.repository;

import br.com.mercadolivre.model.Opiniao;
import br.com.mercadolivre.model.Pagamento;
import br.com.mercadolivre.model.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Query("SELECT p FROM Pagamento p WHERE p.idTransacao = :idTransacao")
    List<Pagamento> findByIdTransacao(Long idTransacao);
}