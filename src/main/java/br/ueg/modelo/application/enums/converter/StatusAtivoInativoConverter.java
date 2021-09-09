/*
 * StatusAtivoInativoConverter.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.application.enums.converter;

import br.ueg.modelo.application.enums.StatusAtivoInativo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Classe de conversão JPA referente ao enum {@link StatusAtivoInativo}.
 * 
 * @author UEG
 */
@Converter(autoApply = true)
public class StatusAtivoInativoConverter implements AttributeConverter<StatusAtivoInativo, String> {

	/**
	 * @see javax.persistence.AttributeConverter#convertToDatabaseColumn(Object)
	 */
	@Override
	public String convertToDatabaseColumn(StatusAtivoInativo status) {
		return status != null ? status.getId() : null;
	}

	/**
	 * @see javax.persistence.AttributeConverter#convertToEntityAttribute(Object)
	 */
	@Override
	public StatusAtivoInativo convertToEntityAttribute(String id) {
		return StatusAtivoInativo.getById(id);
	}

}
