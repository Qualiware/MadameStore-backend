package br.ueg.modelo.application.repository;

import br.ueg.modelo.application.model.Amigo;
import br.ueg.modelo.application.model.Produto;
import br.ueg.modelo.application.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Classe de persistência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryCustom{

    /**
     * Retorna o número de {@link Produto} pelo 'nome' , desconsiderando o
     * 'TipoProduto' com o 'id' informado.
     *
     * @param nome
     * @param idProduto
     * @return
     */
    @Query("SELECT COUNT(produto) FROM Produto produto " +
            " WHERE lower(produto.nome) LIKE lower(:nome)" +
            " AND (:idProduto IS NULL OR produto.id != :idProduto)")
    public Long countByNomeAndNotId(String nome, Long idProduto);

    /**
     * Listar todos os Produtos
     * @return
     */
    @Query("SELECT produto from Produto produto " +
            " INNER JOIN FETCH produto.tipo tipo")
    public List<Produto> getTodos();

    /**
     * Busca uma {@link Produto} pelo id Informado
     *
     * @param idProduto
     * @return
     */
    @Query("SELECT produto from Produto produto " +
            " INNER JOIN FETCH produto.tipo tipo " +
            " WHERE produto.id = :idProduto ")
    public Optional<Produto> findByIdFetch( @Param("idProduto") final Long idProduto);

}
