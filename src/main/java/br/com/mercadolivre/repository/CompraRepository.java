package br.com.mercadolivre.repository;

import br.com.mercadolivre.model.Compra;
import br.com.mercadolivre.model.Opiniao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}
