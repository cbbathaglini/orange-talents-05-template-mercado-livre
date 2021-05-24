package br.com.mercadolivre.repository;

import br.com.mercadolivre.model.Produto;
import br.com.mercadolivre.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT p FROM Produto p WHERE p.id = :idProduto AND p.vendedor.id = :idVendedor ")
    Produto findProdutoByUsuarioId(Long idVendedor, Long idProduto);
}
