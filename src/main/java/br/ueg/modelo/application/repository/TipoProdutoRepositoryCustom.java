/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.application.repository;



import br.ueg.modelo.application.model.Grupo;

import br.ueg.modelo.application.dto.FiltroTipoProdutoDTO;
import br.ueg.modelo.application.model.TipoProduto;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 * 
 * @author UEG
 */
public interface TipoProdutoRepositoryCustom {

	/**
	 * Retorna uma lista de {@link br.ueg.modelo.application.model.TipoProduto} conforme o filtro de pesquisa informado.
	 * 
	 * @param filtroTipoProdutoDTO
	 * @return
	 */
	public List<TipoProduto> findAllByFiltro(FiltroTipoProdutoDTO filtroTipoProdutoDTO);



}
