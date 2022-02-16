package br.ueg.madamestore.application.repository;

import br.ueg.madamestore.application.model.Mensagem;
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
public interface MensagemRepository extends JpaRepository<Mensagem, Long>, MensagemRepositoryCustom{

    /**
     * Retorna o número de {@link Mensagem} pelo 'nome' , desconsiderando o
     * 'Produto' com o 'id' informado.
     *
     *
     * @param idMensagem
     * @return
     */


    /**
     * Listar todos as Vendas
     * @return
     */
    @Query("SELECT mensagem from Mensagem mensagem " +
            " INNER JOIN FETCH mensagem.produto produto")
    public List<Mensagem> getTodos();

    /**
     * Busca uma {@link Mensagem} pelo id Informado
     *
     * @param idMensagem
     * @return
     */
    @Query("SELECT mensagem from Mensagem mensagem " +
            " INNER JOIN FETCH mensagem.produto produto " +
            " WHERE mensagem.id = :idMensagem ")
    public Optional<Mensagem> findByIdFetch( @Param("idMensagem") final Long idMensagem);

}
