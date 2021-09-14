/*
 * TelefoneUsuarioMapper.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.madamestore.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.ueg.madamestore.application.dto.TelefoneUsuarioDTO;
import br.ueg.madamestore.application.model.TelefoneUsuario;

/**
 * Classe adapter referente a entidade {@link TelefoneUsuario}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { UsuarioMapper.class })
public interface TelefoneUsuarioMapper {

	/**
	 * Converte a entidade {@link TelefoneUsuario} em DTO {@link TelefoneUsuarioDTO}.
	 * 
	 * @param telefoneUsuario
	 * @return
	 */
	@Mapping(source = "usuario.id", target = "idUsuario")
	@Mapping(source = "tipo.id", target = "idTipo")
    @Mapping(source = "tipo.descricao", target = "descricaoTipo")
	public TelefoneUsuarioDTO toDTO(TelefoneUsuario telefoneUsuario);

	/**
	 * Converte o DTO {@link TelefoneUsuarioDTO} para entidade {@link TelefoneUsuario}.
	 * 
	 * @param telefoneUsuarioDTO
	 * @return
	 */
	@Mapping(source = "telefoneUsuarioDTO.idUsuario", target = "usuario.id")
	@Mapping(target = "tipo", expression = "java( TipoTelefoneUsuario.getById( telefoneUsuarioDTO.getIdTipo() ) )")
	public TelefoneUsuario toEntity(TelefoneUsuarioDTO telefoneUsuarioDTO);
}
