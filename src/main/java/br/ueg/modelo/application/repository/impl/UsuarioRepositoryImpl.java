/*
 * GrupoRepositoryImpl.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.application.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.ueg.modelo.application.dto.FiltroUsuarioDTO;
import br.ueg.modelo.application.dto.UsuarioDTO;
import br.ueg.modelo.application.model.Usuario;
import br.ueg.modelo.application.repository.UsuarioRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ueg.modelo.comum.util.Util;

/**
 * Classe de persistência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Repository
public class UsuarioRepositoryImpl implements UsuarioRepositoryCustom {

	@Autowired
	private EntityManager entityManager;


	/**
	 * @see UsuarioRepositoryCustom#findAllByLoginIgnoreCaseContaining(String)
	 */
	@Override
	public List<UsuarioDTO> findAllByLoginIgnoreCaseContaining(final String login) {
		StringBuilder jpql = new StringBuilder();
		jpql.append(" SELECT new UsuarioDTO(usuario.id , usuario.login)");
		jpql.append(" FROM Usuario usuario");
		jpql.append(" WHERE usuario.login LIKE ('%' + :login + '%')");

		TypedQuery<UsuarioDTO> query = entityManager.createQuery(jpql.toString(), UsuarioDTO.class);
		query.setParameter("login", login);
		return query.getResultList();
	}
	
	/**
	 * @see UsuarioRepositoryCustom#findAllByFiltro(FiltroUsuarioDTO)
	 * @return
	 */
	@Override
	public List<Usuario> findAllByFiltro(FiltroUsuarioDTO filtroDTO) {
		Map<String, Object> parametros = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" SELECT DISTINCT usuario FROM Usuario usuario " +
				"LEFT JOIN FETCH usuario.grupos ug " +
				"LEFT JOIN FETCH ug.grupo g ");
		jpql.append(" WHERE 1=1 ");
		
		if (!Util.isEmpty(filtroDTO.getLogin())) {
			jpql.append(" AND UPPER(usuario.login) LIKE UPPER('%' || :login || '%') ");
			parametros.put("login", filtroDTO.getLogin());
		}
		
		if (!Util.isEmpty(filtroDTO.getNome())) {
			jpql.append(" AND UPPER(usuario.nome) LIKE UPPER('%' || :nome || '%') ");
			parametros.put("nome", filtroDTO.getNome());
		}

		if (filtroDTO.getStatusEnum() != null) {
			jpql.append(" AND usuario.status = :status ");
			parametros.put("status", filtroDTO.getStatusEnum());
		}

		jpql.append(" ORDER BY usuario.nome ASC ");

		TypedQuery<Usuario> query = entityManager.createQuery(jpql.toString(), Usuario.class);
		parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
		return query.getResultList();
	}
	
}
