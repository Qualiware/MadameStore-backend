/*
 * TipoTelefoneUsuario.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.enums;

import java.util.Arrays;

/**
 * Enum com as possíveis representações de Tipos de Telefone.
 *
 * @author UEG
 */
public enum TipoRetirada {

	PERDA(1L, "Perda"), FURTO(2L, "Furto"), QUEBRA(3L, "Quebra"), VALIDADE(4L, "Validade"), INCLUSAO(5L, "Inclusão");

	private final Long id;

	private final String descricao;

	/**
	 * Construtor da classe.
	 *
	 * @param id
	 * @param descricao
	 */
	TipoRetirada(final Long id, final String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Retorna a instância de {@link TipoRetirada} conforme o 'id' informado.
	 * 
	 * @param id
	 * @return
	 */
	public static TipoRetirada getById(final Long id) {
		return Arrays.stream(values()).filter(value -> value.getId().equals(id)).findFirst().orElse(null);
	}
}
