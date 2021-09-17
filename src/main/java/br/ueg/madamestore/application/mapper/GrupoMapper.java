package br.ueg.madamestore.application.mapper;


import org.mapstruct.Mapper;

import br.ueg.madamestore.application.dto.GrupoDTO;
import br.ueg.madamestore.application.model.Grupo;

/**
 * Classe adapter referente a entidade {@link Grupo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { StatusAtivoInativoMapper.class, SimNaoMapper.class, FuncionalidadeMapper.class})
public interface GrupoMapper {
    /**
     * Converte a entidade {@link Grupo} em DTO {@link GrupoDTO}
     *
     * @param grupo
     * @return
     */

    public GrupoDTO toDTO(Grupo grupo);

    /**
     * Converte o DTO {@link GrupoDTO} para entidade {@link Grupo}
     *
     * @param grupoDTO
     * @return
     */

   // @Mapping(target = "status", expression = "java( StatusAtivoInativo.getById( grupoDTO.getIdStatus() ) )")
    public Grupo toEntity(GrupoDTO grupoDTO);
}
