/*
 * TipoTelefoneUsuarioConverter.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.enums.converter;

import br.ueg.madamestore.application.enums.TipoRetirada;
import br.ueg.madamestore.application.enums.TipoTelefoneUsuario;
import br.ueg.madamestore.application.model.Mensagem;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Classe de conversão JPA referente ao enum {@TipoRetirada}.
 * 
 * @author UEG
 */
@Converter(autoApply = true)
public class TipoRetiradaConverter implements AttributeConverter<TipoRetirada, Long> {

	/**
	 * @see AttributeConverter#convertToDatabaseColumn(Object)
	 */
	@Override
	public Long convertToDatabaseColumn(final TipoRetirada tipo) {
		return tipo != null ? tipo.getId() : null;
	}

	/**
	 * @see AttributeConverter#convertToEntityAttribute(Object)
	 */
	@Override
	public TipoRetirada convertToEntityAttribute(Long id) {
		return TipoRetirada.getById(id);
	}

}
