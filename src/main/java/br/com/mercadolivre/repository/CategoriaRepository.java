package br.com.mercadolivre.repository;

import br.com.mercadolivre.model.Categoria;
import br.com.mercadolivre.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository  extends JpaRepository<Categoria, Long> {
}
