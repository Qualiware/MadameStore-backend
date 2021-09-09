package br.ueg.modelo.application.mapper;


import br.ueg.modelo.application.dto.ProdutoDTO;
import br.ueg.modelo.application.dto.TipoProdutoDTO;
import br.ueg.modelo.application.model.Produto;
import br.ueg.modelo.application.model.TipoProduto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
