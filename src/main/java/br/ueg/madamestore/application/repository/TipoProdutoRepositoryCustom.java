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

import br.ueg.madamestore.application.dto.FiltroTipoProdutoDTO;
import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.application.model.TipoProduto;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 * 
 * @author UEG
 */
public interface TipoProdutoRepositoryCustom {

	/**
	 * Retorna uma lista de {@link br.ueg.madamestore.application.model.TipoProduto} conforme o filtro de pesquisa informado.
	 * 
	 * @param filtroTipoProdutoDTO
	 * @return
	 */
	public List<TipoProduto> findAllByFiltro(FiltroTipoProdutoDTO filtroTipoProdutoDTO);



}
