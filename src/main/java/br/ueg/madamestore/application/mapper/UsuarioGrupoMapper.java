/*
 * UsuarioGrupoMapper.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.madamestore.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.ueg.madamestore.application.dto.UsuarioGrupoDTO;
import br.ueg.madamestore.application.model.UsuarioGrupo;

/**
 * Classe adapter referente a entidade {@link UsuarioGrupo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { SimNaoMapper.class, UsuarioMapper.class, GrupoMapper.class })
public interface UsuarioGrupoMapper {
	/**
	 * Converte a entidade {@link UsuarioGrupo} em DTO {@link UsuarioGrupoDTO}.
	 *
	 * @param usuarioGrupo
	 * @return
	 */
	@Mapping(source = "usuario.id", target = "idUsuario")
	@Mapping(source = "grupo.id", target = "idGrupo")
	@Mapping(source = "grupo.nome", target = "nomeGrupo")
	public UsuarioGrupoDTO toDTO(UsuarioGrupo usuarioGrupo);

	/**
	 * Converte o DTO {@link UsuarioGrupoDTO} para entidade {@link UsuarioGrupo}.
	 *
	 * @param usuarioGrupoDTO
	 * @return
	 */
	@Mapping(source = "usuarioGrupoDTO.idUsuario", target = "usuario.id")
	@Mapping(source = "usuarioGrupoDTO.idGrupo", target = "grupo.id")
	public UsuarioGrupo toEntity(UsuarioGrupoDTO usuarioGrupoDTO);
}
