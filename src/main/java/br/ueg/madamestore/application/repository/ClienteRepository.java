package br.ueg.madamestore.application.repository;

import br.ueg.madamestore.application.model.Cliente;
import br.ueg.madamestore.application.model.Usuario;
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
public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepositoryCustom{

    /**
     * Retorna o número de {@link Cliente} pelo 'nome' , desconsiderando o
     * 'TipoCliente' com o 'id' informado.
     *
     * @param nome
     * @param idCliente
     * @return
     */
    @Query("SELECT COUNT(cliente) FROM Cliente cliente " +
            " WHERE lower(cliente.nome) LIKE lower(:nome)" +
            " AND (:idCliente IS NULL OR cliente.id != :idCliente)")
    public Long countByNomeAndNotId(String nome, Long idCliente);

    /**
     * Listar todos os Clientes
     * @return
     */
    @Query("SELECT cliente from Cliente cliente")
    public List<Cliente> getTodos();

    /**
     * Busca uma {@link Cliente} pelo id Informado
     *
     * @param idCliente
     * @return
     */
    @Query("SELECT cliente from Cliente cliente " +
            " WHERE cliente.id = :idCliente ")
    public Optional<Cliente> findByIdFetch( @Param("idCliente") final Long idCliente);

}
