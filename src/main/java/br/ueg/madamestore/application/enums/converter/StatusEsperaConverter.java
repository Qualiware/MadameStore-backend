/*
 * StatusSimNaoConverter.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.enums.converter;

import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusSimNao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Classe de conversão JPA referente ao enum {@link StatusEspera}.
 * 
 * @author UEG
 */
@Converter(autoApply = true)
public class StatusEsperaConverter implements AttributeConverter<StatusEspera, String> {

	/**
	 * @see AttributeConverter#convertToDatabaseColumn(Object)
	 */
	@Override
	public String convertToDatabaseColumn(final StatusEspera status) {
		return status != null ? status.getId() : null;
	}

	/**
	 * @see AttributeConverter#convertToEntityAttribute(Object)
	 */
	@Override
	public StatusEspera convertToEntityAttribute(String id) {
		return StatusEspera.getById(id);
	}

}
