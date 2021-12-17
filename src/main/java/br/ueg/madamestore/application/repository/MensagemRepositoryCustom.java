/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.repository;


import br.ueg.madamestore.application.dto.FiltroMensagemDTO;
import br.ueg.madamestore.application.dto.FiltroVendaDTO;
import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.application.model.Mensagem;
import br.ueg.madamestore.application.model.Venda;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 * 
 * @author UEG
 */
public interface MensagemRepositoryCustom {

	/**
	 * Retorna uma lista de {@link Venda} conforme o filtro de pesquisa informado.
	 * 
	 * @param filtroMensagemDTO
	 * @return
	 */
	public List<Mensagem> findAllByFiltro(FiltroMensagemDTO filtroMensagemDTO);



}
