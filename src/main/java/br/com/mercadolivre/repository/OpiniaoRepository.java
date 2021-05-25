package br.com.mercadolivre.repository;

import br.com.mercadolivre.model.Opiniao;
import br.com.mercadolivre.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpiniaoRepository extends JpaRepository<Opiniao, Long> {
}
