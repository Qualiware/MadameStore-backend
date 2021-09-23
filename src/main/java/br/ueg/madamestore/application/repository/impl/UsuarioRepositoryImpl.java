/*
 * GrupoRepositoryImpl.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ueg.madamestore.application.dto.FiltroUsuarioDTO;
import br.ueg.madamestore.application.dto.UsuarioDTO;
import br.ueg.madamestore.application.model.Usuario;
import br.ueg.madamestore.application.repository.UsuarioRepositoryCustom;
import br.ueg.madamestore.comum.util.Util;

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
	public List<UsuarioDTO> findAllByEmailIgnoreCaseContaining(final String email) {
		StringBuilder jpql = new StringBuilder();
		jpql.append(" SELECT new UsuarioDTO(usuario.id , usuario.email)");
		jpql.append(" FROM Usuario usuario");
		jpql.append(" WHERE usuario.email LIKE ('%' + :email + '%')");

		TypedQuery<UsuarioDTO> query = entityManager.createQuery(jpql.toString(), UsuarioDTO.class);
		query.setParameter("email", email);
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
		
		if (!Util.isEmpty(filtroDTO.getCpf())) {
			jpql.append(" AND numr_cpf LIKE '%' || :numr_cpf || '%' ");
			parametros.put("numr_cpf", filtroDTO.getCpf());
		}

		if (!Util.isEmpty(filtroDTO.getEmail())) {
			jpql.append(" AND email LIKE '%' || :email || '%' ");
			parametros.put("email", filtroDTO.getEmail());
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
