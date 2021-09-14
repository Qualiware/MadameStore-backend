/*
 * UsuarioMapper.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.madamestore.application.mapper;

import org.mapstruct.Mapper;

import br.ueg.madamestore.application.dto.UsuarioDTO;
import br.ueg.madamestore.application.model.Usuario;

/**
 * Classe adapter referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { StatusAtivoInativoMapper.class, SimNaoMapper.class, UsuarioGrupoMapper.class, TelefoneUsuarioMapper.class })
public interface UsuarioMapper {
	/**
	 * Converte a entidade {@link Usuario} em DTO {@link UsuarioDTO}
	 *
	 * @param usuario
	 * @return
	 */

	public UsuarioDTO toDTO(Usuario usuario);

	/**
	 * Converte o DTO {@link UsuarioDTO} para entidade {@link Usuario}
	 *
	 * @param usuarioDTO
	 * @return
	 */
	public Usuario toEntity(UsuarioDTO usuarioDTO);
}
