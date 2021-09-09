/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.application.repository;


import br.ueg.modelo.application.dto.FiltroTipoAmigoDTO;
import br.ueg.modelo.application.model.Grupo;
import br.ueg.modelo.application.model.TipoAmigo;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 * 
 * @author UEG
 */
public interface TipoAmigoRepositoryCustom {

	/**
	 * Retorna uma lista de {@link TipoAmigo} conforme o filtro de pesquisa informado.
	 * 
	 * @param filtroTipoAmigoDTO
	 * @return
	 */
	public List<TipoAmigo> findAllByFiltro(FiltroTipoAmigoDTO filtroTipoAmigoDTO);



}
