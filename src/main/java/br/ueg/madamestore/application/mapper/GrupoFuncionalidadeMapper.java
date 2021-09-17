package br.ueg.madamestore.application.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.ueg.madamestore.application.dto.GrupoFuncionalidadeDTO;
import br.ueg.madamestore.application.model.GrupoFuncionalidade;

/**
 * Classe adapter referente a entidade {@link GrupoFuncionalidade}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { SimNaoMapper.class, FuncionalidadeMapper.class, GrupoMapper.class})
public interface GrupoFuncionalidadeMapper {
    /**
     * Converte a entidade {@link GrupoFuncionalidade} em DTO {@link GrupoFuncionalidadeDTO}
     *
     * @param grupoFuncionalidade
     * @return
     */
    @Mapping(source = "grupo.id", target = "idGrupo")
    //@Mapping(source = "funcionalidade.id", target = "idFuncionalidade")
    public GrupoFuncionalidadeDTO toDTO(GrupoFuncionalidade grupoFuncionalidade);

    /**
     * Converte o DTO {@link GrupoFuncionalidadeDTO} para entidade {@link GrupoFuncionalidade}
     *
     * @param grupoFuncionalidadeDTO
     * @return
     */
    @Mapping(source = "grupoFuncionalidadeDTO.idGrupo", target = "grupo.id")
    //@Mapping(source = "grupoFuncionalidadeDTO.idFuncionalidade", target = "funcionalidade.id")
    public GrupoFuncionalidade toEntity(GrupoFuncionalidadeDTO grupoFuncionalidadeDTO);
}
