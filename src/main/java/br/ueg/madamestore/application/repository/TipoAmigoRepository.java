package br.ueg.madamestore.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ueg.madamestore.application.enums.StatusAtivoInativo;
import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.application.model.TipoAmigo;
import br.ueg.madamestore.application.model.Usuario;

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
