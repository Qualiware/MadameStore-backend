/*
 * StatusSimNaoConverter.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.enums.converter;

import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.enums.StatusVendido;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Classe de conversão JPA referente ao enum {@link StatusVendido}.
 * 
 * @author UEG
 */
@Converter(autoApply = true)
public class StatusVendidoConverter implements AttributeConverter<StatusVendido, String> {

	/**
	 * @see AttributeConverter#convertToDatabaseColumn(Object)
	 */
	@Override
	public String convertToDatabaseColumn(final StatusVendido status) {
		return status != null ? status.getId() : null;
	}

	/**
	 * @see AttributeConverter#convertToEntityAttribute(Object)
	 */
	@Override
	public StatusVendido convertToEntityAttribute(String id) {
		return StatusVendido.getById(id);
	}

}
