package br.ueg.madamestore.application.mapper;


import org.mapstruct.Mapper;

import br.ueg.madamestore.application.dto.TipoAmigoDTO;
import br.ueg.madamestore.application.dto.TipoProdutoDTO;
import br.ueg.madamestore.application.model.Modulo;
import br.ueg.madamestore.application.model.TipoAmigo;
import br.ueg.madamestore.application.model.TipoProduto;

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
