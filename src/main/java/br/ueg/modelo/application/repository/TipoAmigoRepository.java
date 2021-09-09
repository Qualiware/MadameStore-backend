package br.ueg.modelo.application.repository;

import br.ueg.modelo.application.enums.StatusAtivoInativo;
import br.ueg.modelo.application.model.Grupo;
import br.ueg.modelo.application.model.TipoAmigo;
import br.ueg.modelo.application.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Repository
public interface TipoAmigoRepository extends JpaRepository<TipoAmigo, Long>, TipoAmigoRepositoryCustom{

    /**
     * Retorna o número de {@link TipoAmigo} pelo 'nome' , desconsiderando o
     * 'TipoAmigo' com o 'id' informado.
     *
     * @param nome
     * @param idTipoAmigo
     * @return
     */
    @Query("SELECT COUNT(tipoAmigo) FROM TipoAmigo tipoAmigo " +
            " WHERE lower(tipoAmigo.nome) LIKE lower(:nome)" +
            " AND (:idTipoAmigo IS NULL OR tipoAmigo.id != :idTipoAmigo)")
    public Long countByNomeAndNotId(String nome, Long idTipoAmigo);

}
