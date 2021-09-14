/*
 * Validation.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.api.util;

import java.math.BigDecimal;

import br.ueg.madamestore.api.exception.InvalidParameterException;
import br.ueg.madamestore.comum.util.Util;

/**
 * Classe 'Validation' para solucionar a limitação do Spring-boot na verificação
 * de parâmetros informados na URL.
 *
 * @author UEG
 */
public final class Validation {

	/**
	 * Construtor privado para garantir o singleton.
	 */
	private Validation() {

	}

	/**
	 * Verifica se o 'value' respeita o tamanho máximo definido no parâmetro
	 * 'maxValue'.
	 * 
	 * @param field -
	 * @param value -
	 * @param maxValue -
	 */
	public static void max(final String field, final BigDecimal value, final Long maxValue)
			throws InvalidParameterException {

		if (Util.isEmpty(field) || value == null || maxValue == null) {
			throw new IllegalArgumentException("Os parâmetros devem ser especificados.");
		}

		if (value.compareTo(BigDecimal.valueOf(maxValue)) > BigDecimal.ZERO.intValue()) {
			throw new InvalidParameterException(field, "deve ser menor ou igual a " + maxValue.toString().length());
		}
	}
}
