/*
 * TelefoneUsuarioMapper.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.madamestore.application.mapper;

import br.ueg.madamestore.application.dto.MensagemDTO;
import br.ueg.madamestore.application.dto.TelefoneUsuarioDTO;
import br.ueg.madamestore.application.model.Mensagem;
import br.ueg.madamestore.application.model.TelefoneUsuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Classe adapter referente a entidade {@link TelefoneUsuario}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { UsuarioMapper.class })
public interface MensagemMapper {

	/**
	 * Converte a entidade {@link Mensagem} em DTO {@link MensagemDTO}.
	 * 
	 * @param mensagem
	 * @return
	 */

	@Mapping(source = "tipo.id", target = "idTipo")
    @Mapping(source = "tipo.descricao", target = "descricaoTipo")
	@Mapping(source = "produto.id", target = "idProduto")
	@Mapping(source = "produto.nome", target = "nomeProduto")
	public MensagemDTO toDTO(Mensagem mensagem);

	/**
	 * Converte o DTO {@link TelefoneUsuarioDTO} para entidade {@link TelefoneUsuario}.
	 * 
	 * @param mensagemDTO
	 * @return
	 */

	@Mapping(target = "tipo", expression = "java( TipoRetirada.getById( mensagemDTO.getIdTipo() ) )")
	@Mapping(source = "mensagemDTO.idProduto", target = "produto.id")
	public Mensagem toEntity(MensagemDTO mensagemDTO);
}
