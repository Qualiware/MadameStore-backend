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

import br.ueg.madamestore.application.dto.FiltroAmigoDTO;
import br.ueg.madamestore.application.model.Amigo;
import br.ueg.madamestore.application.model.Grupo;

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
