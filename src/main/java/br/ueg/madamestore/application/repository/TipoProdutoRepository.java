package br.ueg.madamestore.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ueg.madamestore.application.model.TipoProduto;
import br.ueg.madamestore.application.model.Usuario;

/**
 * Classe de persistência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Repository
public interface TipoProdutoRepository extends JpaRepository<TipoProduto, Long>, TipoProdutoRepositoryCustom{

    /**
     * Retorna o número de {@link TipoProduto} pelo 'nome' , desconsiderando o
     * 'TipoProduto' com o 'id' informado.
     *
     * @param nome
     * @param idTipoProduto
     * @return
     */
    @Query("SELECT COUNT(tipoProduto) FROM TipoProduto tipoProduto " +
            " WHERE lower(tipoProduto.nome) LIKE lower(:nome)" +
            " AND (:idTipoProduto IS NULL OR tipoProduto.id != :idTipoProduto)")
    public Long countByNomeAndNotId(String nome, Long idTipoProduto);

}
