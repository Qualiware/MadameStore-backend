package br.ueg.madamestore.application.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.ueg.madamestore.application.dto.ProdutoDTO;
import br.ueg.madamestore.application.dto.TipoProdutoDTO;
import br.ueg.madamestore.application.model.Produto;
import br.ueg.madamestore.application.model.TipoProduto;

/**
 * Classe adapter referente a entidade {@link Produto}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { SimNaoMapper.class})
public interface ProdutoMapper {
    /**
     * Converte a entidade {@link Produto} em DTO {@link ProdutoDTO}
     *
     * @param produto
     * @return
     */
    @Mapping(source = "tipo.id", target = "idTipoProduto")
    @Mapping(source = "tipo.nome", target = "nomeTipoProduto")
    public ProdutoDTO toDTO(Produto produto);

    /**
     * Converte o DTO {@link TipoProdutoDTO} para entidade {@link TipoProduto}
     *
     * @param produtoDTO
     * @return
     */
    @Mapping(source = "produtoDTO.idTipoProduto", target = "tipo.id")
    public Produto toEntity(ProdutoDTO produtoDTO);
}
