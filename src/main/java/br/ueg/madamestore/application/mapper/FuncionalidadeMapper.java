package br.ueg.madamestore.application.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.ueg.madamestore.application.dto.FuncionalidadeDTO;
import br.ueg.madamestore.application.model.Funcionalidade;

/**
 * Classe adapter referente a entidade {@link Funcionalidade}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { SimNaoMapper.class})
public interface FuncionalidadeMapper {
    /**
     * Converte a entidade {@link Funcionalidade} em DTO {@link FuncionalidadeDTO}
     *
     * @param funcionalidade
     * @return
     */

    @Mapping(source = "status.id", target = "idStatus")
    @Mapping(source = "status.descricao", target = "descricaoStatus")
    public FuncionalidadeDTO toDTO(Funcionalidade funcionalidade);

    /**
     * Converte o DTO {@link FuncionalidadeDTO} para entidade {@link Funcionalidade}
     *
     * @param funcionalidadeDTO
     * @return
     */

    @Mapping(target = "status", expression = "java( StatusAtivoInativo.getById( funcionalidadeDTO.getIdStatus() ) )")

    public Funcionalidade toEntity(FuncionalidadeDTO funcionalidadeDTO);
}
