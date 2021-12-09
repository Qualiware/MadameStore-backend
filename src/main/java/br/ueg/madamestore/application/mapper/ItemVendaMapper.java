/*
 * UsuarioGrupoMapper.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.madamestore.application.mapper;

import br.ueg.madamestore.application.dto.ItemVendaDTO;
import br.ueg.madamestore.application.dto.UsuarioGrupoDTO;
import br.ueg.madamestore.application.model.ItemVenda;
import br.ueg.madamestore.application.model.UsuarioGrupo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Classe adapter referente a entidade {@link UsuarioGrupo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = {ProdutoMapper.class, VendaMapper.class })
public interface ItemVendaMapper {
	/**
	 * Converte a entidade {@link ItemVenda} em DTO {@link br.ueg.madamestore.application.dto.ItemVendaDTO}.
	 *
	 * @param itemVenda
	 * @return
	 */
	@Mapping(source = "venda.id", target = "idVenda")
	@Mapping(source = "produto.id", target = "idProduto")
	@Mapping(source = "produto.nome", target = "nomeProduto")
	@Mapping(source = "produto.preco", target = "preco")
	@Mapping(source = "produto.quantidade", target = "quantidade")
	@Mapping(source = "produto.vliquido", target = "vliquido")
	public ItemVendaDTO toDTO(ItemVenda itemVenda);

	/**
	 * Converte o DTO {@link ItemVendaDTO} para entidade {@link ItemVenda}.
	 *
	 * @param itemVendaDTO
	 * @return
	 */
	@Mapping(source = "itemVendaDTO.idVenda", target = "venda.id")
	@Mapping(source = "itemVendaDTO.idProduto", target = "produto.id")
	public ItemVenda toEntity(ItemVendaDTO itemVendaDTO);
}
