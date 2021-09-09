/*
 * UsuarioMapper.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.modelo.application.mapper;

import br.ueg.modelo.application.dto.UsuarioDTO;
import br.ueg.modelo.application.model.Usuario;
import org.mapstruct.Mapper;

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
