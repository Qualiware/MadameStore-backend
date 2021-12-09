package br.ueg.madamestore.application.mapper;


import org.mapstruct.Mapper;

import br.ueg.madamestore.application.dto.TipoAmigoDTO;
import br.ueg.madamestore.application.model.Modulo;
import br.ueg.madamestore.application.model.TipoAmigo;

/**
 * Classe adapter referente a entidade {@link Modulo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring")
public interface TipoAmigoMapper {
    /**
     * Converte a entidade {@link TipoAmigo} em DTO {@link TipoAmigoDTO}
     *
     * @param tipoAmigo
     * @return
     */

    public TipoAmigoDTO toDTO(TipoAmigo tipoAmigo);

    /**
     * Converte o DTO {@link TipoAmigoDTO} para entidade {@link TipoAmigo}
     *
     * @param tipoAmigoDTO
     * @return
     */
    public TipoAmigo toEntity(TipoAmigoDTO tipoAmigoDTO);
}
