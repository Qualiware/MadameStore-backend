/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.repository;


import br.ueg.madamestore.application.dto.FiltroVendaDTO;
import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.application.model.Venda;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 * 
 * @author UEG
 */
public interface VendaRepositoryCustom {

	/**
	 * Retorna uma lista de {@link Venda} conforme o filtro de pesquisa informado.
	 * 
	 * @param filtroVendaDTO
	 * @return
	 */
	public List<Venda> findAllByFiltro(FiltroVendaDTO filtroVendaDTO);



}
