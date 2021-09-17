package br.ueg.madamestore.application.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.ueg.madamestore.application.dto.ModuloDTO;
import br.ueg.madamestore.application.model.Modulo;

/**
 * Classe adapter referente a entidade {@link Modulo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { SimNaoMapper.class, FuncionalidadeMapper.class})
public interface ModuloMapper {
    /**
     * Converte a entidade {@link Modulo} em DTO {@link ModuloDTO}
     *
     * @param modulo
     * @return
     */

    @Mapping(source = "status.id", target = "idStatus")
    @Mapping(source = "status.descricao", target = "descricaoStatus")
    public ModuloDTO toDTO(Modulo modulo);

    /**
     * Converte o DTO {@link ModuloDTO} para entidade {@link Modulo}
     *
     * @param moduloDTO
     * @return
     */

    @Mapping(target = "status", expression = "java( StatusAtivoInativo.getById( moduloDTO.getIdStatus() ) )")

    public Modulo toEntity(ModuloDTO moduloDTO);
}
