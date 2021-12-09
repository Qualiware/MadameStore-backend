/*
 * ModuloRepository.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ueg.madamestore.application.model.Modulo;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Modulo}.
 *
 * @author UEG
 */
@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Long> {

	/**
	 * Retorna uma lista de {@link Modulo} ativos conforme o 'id' do Sistema informado.
	 *
	 * @return
	 */
	@Query(" SELECT DISTINCT modulo FROM Modulo modulo INNER JOIN FETCH modulo.funcionalidades funcionalidades"
			+ " WHERE modulo.status = 'A'"
			+ " ORDER BY modulo.nome ASC, funcionalidades.nome ASC ")
	public List<Modulo> getAtivos();

}
