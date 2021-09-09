/*
 * StatusSimNaoConverter.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.application.enums.converter;

import br.ueg.modelo.application.enums.StatusSimNao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Classe de conversão JPA referente ao enum {@link StatusSimNao}.
 * 
 * @author UEG
 */
@Converter(autoApply = true)
public class StatusSimNaoConverter implements AttributeConverter<StatusSimNao, String> {

	/**
	 * @see javax.persistence.AttributeConverter#convertToDatabaseColumn(Object)
	 */
	@Override
	public String convertToDatabaseColumn(final StatusSimNao status) {
		return status != null ? status.getId() : null;
	}

	/**
	 * @see javax.persistence.AttributeConverter#convertToEntityAttribute(Object)
	 */
	@Override
	public StatusSimNao convertToEntityAttribute(String id) {
		return StatusSimNao.getById(id);
	}

}
