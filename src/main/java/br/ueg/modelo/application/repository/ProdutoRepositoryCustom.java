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
import br.ueg.modelo.application.dto.FiltroProdutoDTO;
import br.ueg.modelo.application.model.Amigo;
import br.ueg.modelo.application.model.Grupo;
import br.ueg.modelo.application.model.Produto;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 * 
 * @author UEG
 */
public interface ProdutoRepositoryCustom {

	/**
	 * Retorna uma lista de {@link br.ueg.modelo.application.model.Produto} conforme o filtro de pesquisa informado.
	 * 
	 * @param filtroProdutoDTO
	 * @return
	 */
	public List<Produto> findAllByFiltro(FiltroProdutoDTO filtroProdutoDTO);



}
