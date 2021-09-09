/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.application.repository;


import br.ueg.modelo.application.dto.FiltroAmigoDTO;
import br.ueg.modelo.application.model.Amigo;
import br.ueg.modelo.application.model.Grupo;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 * 
 * @author UEG
 */
public interface AmigoRepositoryCustom {

	/**
	 * Retorna uma lista de {@link Amigo} conforme o filtro de pesquisa informado.
	 * 
	 * @param filtroAmigoDTO
	 * @return
	 */
	public List<Amigo> findAllByFiltro(FiltroAmigoDTO filtroAmigoDTO);



}
