package br.ueg.madamestore.application.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.ueg.madamestore.application.dto.AmigoDTO;
import br.ueg.madamestore.application.dto.TipoAmigoDTO;
import br.ueg.madamestore.application.model.Amigo;
import br.ueg.madamestore.application.model.TipoAmigo;

/**
 * Classe adapter referente a entidade {@link Amigo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { SimNaoMapper.class})
public interface AmigoMapper {
    /**
     * Converte a entidade {@link Amigo} em DTO {@link AmigoDTO}
     *
     * @param amigo
     * @return
     */
    @Mapping(source = "tipo.id", target = "idTipoAmigo")
    @Mapping(source = "tipo.nome", target = "nomeTipoAmigo")
    public AmigoDTO toDTO(Amigo amigo);

    /**
     * Converte o DTO {@link TipoAmigoDTO} para entidade {@link TipoAmigo}
     *
     * @param amigoDTO
     * @return
     */
    @Mapping(source = "amigoDTO.idTipoAmigo", target = "tipo.id")
    public Amigo toEntity(AmigoDTO amigoDTO);
}
