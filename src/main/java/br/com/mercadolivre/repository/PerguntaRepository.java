package br.com.mercadolivre.repository;

import br.com.mercadolivre.model.Pergunta;
import br.com.mercadolivre.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
}
