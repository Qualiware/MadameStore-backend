/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.repository;


import java.util.List;

import br.ueg.madamestore.application.dto.FiltroTipoAmigoDTO;
import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.application.model.TipoAmigo;

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
