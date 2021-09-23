/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.repository;

import br.ueg.madamestore.application.dto.FiltroClienteDTO;
import br.ueg.madamestore.application.model.Cliente;
import br.ueg.madamestore.application.model.Grupo;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 * 
 * @author UEG
 */
public interface ClienteRepositoryCustom {

	/**
	 * Retorna uma lista de {@link br.ueg.madamestore.application.model.Cliente} conforme o filtro de pesquisa informado.
	 * 
	 * @param filtroClienteDTO
	 * @return
	 */
	public List<Cliente> findAllByFiltro(FiltroClienteDTO filtroClienteDTO);



}
