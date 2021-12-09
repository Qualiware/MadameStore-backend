package br.ueg.madamestore.application.enums;

import java.util.Arrays;

/**
 * Enum com as possíveis representações de Status Sim/Não.
 *
 * @author UEG
 */
public enum StatusEspera {

	SIM("S", "Sim"), NAO("N", "Não");

	private final String id;

	private final String descricao;

	/**
	 * Construtor da classe.
	 *
	 * @param id -
	 * @param descricao -
	 */
	StatusEspera(final String id, final String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Retorna a instância de {@link StatusEspera} conforme o 'id' informado.
	 *
	 * @param id -
	 * @return -
	 */
	public static StatusEspera getById(final String id) {
		return Arrays.stream(values()).filter(value -> value.getId().equals(id)).findFirst().orElse(null);
	}


	/**
	 * Retorna a instância de true ou false conforme o 'id' informado.
	 *
	 * @param idb -
	 * @return -
	 */
	public static StatusEspera getByIdStatusSimNao(final boolean idb) {
		String id = idb ? "S" : "N";
		return Arrays.stream(values()).filter(value -> value.getId().equals(id)).findFirst().orElse(null);
	}

}
