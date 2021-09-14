/*
 * ModuloService.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.madamestore.application.dto.ModuloDTO;
import br.ueg.madamestore.application.exception.SistemaMessageCode;
import br.ueg.madamestore.application.model.Modulo;
import br.ueg.madamestore.application.repository.ModuloRepository;
import br.ueg.madamestore.comum.exception.BusinessException;
import br.ueg.madamestore.comum.util.CollectionUtil;

import java.util.List;

/**
 * Classe de négocio referente a entidade {@link Modulo}.
 *
 * @author UEG
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ModuloService {

	@Autowired
	private ModuloRepository moduloRepository;

	/**
	 * Retorna uma lista de {@link ModuloDTO} ativos.
	 *
	 * @return
	 */
	public List<Modulo> getAtivos() {
		List<Modulo> modulos = moduloRepository.getAtivos();

		if (CollectionUtil.isEmpty(modulos)) {
			throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
		}
		return modulos;
	}

	/**
	 * Retorna uma lista de {@link ModuloDTO} cadastrados.
	 *
	 * @return
	 */
	public List<Modulo> getTodos() {
		List<Modulo> modulos = moduloRepository.findAll();

		if (CollectionUtil.isEmpty(modulos)) {
			throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
		}
		return modulos;
	}
}
