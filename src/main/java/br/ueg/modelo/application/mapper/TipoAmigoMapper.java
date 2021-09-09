package br.ueg.modelo.application.mapper;


import br.ueg.modelo.application.dto.TipoAmigoDTO;
import br.ueg.modelo.application.model.Modulo;
import br.ueg.modelo.application.model.TipoAmigo;
import org.mapstruct.Mapper;

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
