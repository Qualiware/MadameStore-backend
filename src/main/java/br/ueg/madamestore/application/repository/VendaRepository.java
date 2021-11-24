package br.ueg.madamestore.application.repository;

import br.ueg.madamestore.application.model.Produto;
import br.ueg.madamestore.application.model.Usuario;
import br.ueg.madamestore.application.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Classe de persistência referente a entidade {@link Venda}.
 *
 * @author UEG
 */
@Repository
public interface VendaRepository extends JpaRepository<Venda, Long>, VendaRepositoryCustom{

    /**
     * Retorna o número de {@link Venda} pelo 'nome' , desconsiderando o
     * 'Produto' com o 'id' informado.
     *
     *
     * @param idVenda
     * @return
     */


    /**
     * Listar todos as Vendas
     * @return
     */
    @Query("SELECT venda from Venda venda " +
            " INNER JOIN FETCH venda.itemVenda produto")
    public List<Venda> getTodos();

    /**
     * Busca uma {@link Venda} pelo id Informado
     *
     * @param idVenda
     * @return
     */
    @Query("SELECT venda from Venda venda " +
            " INNER JOIN FETCH venda.itemVenda produto " +
            " WHERE venda.id = :idVenda ")
    public Optional<Venda> findByIdFetch( @Param("idVenda") final Long idVenda);

}
