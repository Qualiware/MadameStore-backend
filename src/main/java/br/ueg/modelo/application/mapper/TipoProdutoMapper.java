package br.ueg.modelo.application.mapper;


import br.ueg.modelo.application.dto.TipoAmigoDTO;
import br.ueg.modelo.application.dto.TipoProdutoDTO;
import br.ueg.modelo.application.model.Modulo;
import br.ueg.modelo.application.model.TipoAmigo;
import br.ueg.modelo.application.model.TipoProduto;
import org.mapstruct.Mapper;

/**
 * Classe adapter referente a entidade {@link Modulo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring")
public interface TipoProdutoMapper {
    /**
     * Converte a entidade {@link TipoProduto} em DTO {@link TipoProdutoDTO}
     *
     * @param tipoProduto
     * @return
     */

    public TipoProdutoDTO toDTO(TipoProduto tipoProduto);

    /**
     * Converte o DTO {@link TipoProdutoDTO} para entidade {@link TipoProduto}
     *
     * @param tipoProdutoDTO
     * @return
     */
    public TipoProduto toEntity(TipoProdutoDTO tipoProdutoDTO);
}
