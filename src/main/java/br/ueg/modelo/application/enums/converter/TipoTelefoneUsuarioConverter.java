/*
 * TipoTelefoneUsuarioConverter.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.application.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.ueg.modelo.application.enums.TipoTelefoneUsuario;

/**
 * Classe de conversão JPA referente ao enum {@link TipoTelefoneUsuario}.
 * 
 * @author UEG
 */
@Converter(autoApply = true)
public class TipoTelefoneUsuarioConverter implements AttributeConverter<TipoTelefoneUsuario, Long> {

	/**
	 * @see AttributeConverter#convertToDatabaseColumn(Object)
	 */
	@Override
	public Long convertToDatabaseColumn(final TipoTelefoneUsuario tipo) {
		return tipo != null ? tipo.getId() : null;
	}

	/**
	 * @see AttributeConverter#convertToEntityAttribute(Object)
	 */
	@Override
	public TipoTelefoneUsuario convertToEntityAttribute(Long id) {
		return TipoTelefoneUsuario.getById(id);
	}

}
