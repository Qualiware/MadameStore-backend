package br.ueg.modelo.application.repository;

import br.ueg.modelo.application.model.Amigo;
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
public interface AmigoRepository extends JpaRepository<Amigo, Long>, AmigoRepositoryCustom{

    /**
     * Retorna o número de {@link Amigo} pelo 'nome' , desconsiderando o
     * 'TipoAmigo' com o 'id' informado.
     *
     * @param nome
     * @param idAmigo
     * @return
     */
    @Query("SELECT COUNT(amigo) FROM Amigo amigo " +
            " WHERE lower(amigo.nome) LIKE lower(:nome)" +
            " AND (:idAmigo IS NULL OR amigo.id != :idAmigo)")
    public Long countByNomeAndNotId(String nome, Long idAmigo);

    /**
     * Listar todos os Amigos
     * @return
     */
    @Query("SELECT amigo from Amigo amigo " +
            " INNER JOIN FETCH amigo.tipo tipo")
    public List<Amigo> getTodos();

    /**
     * Busca uma {@link Amigo} pelo id Informado
     *
     * @param idAmigo
     * @return
     */
    @Query("SELECT amigo from Amigo amigo " +
            " INNER JOIN FETCH amigo.tipo tipo " +
            " WHERE amigo.id = :idAmigo ")
    public Optional<Amigo> findByIdFetch( @Param("idAmigo") final Long idAmigo);

}
