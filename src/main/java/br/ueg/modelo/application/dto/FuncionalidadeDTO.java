/*
 * UsuarioTO.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.modelo.application.dto;

import br.ueg.modelo.comum.util.Util;
import br.ueg.modelo.application.model.Funcionalidade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Classe de transferência referente a entidade {@link Funcionalidade}.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Funcionalidade")
public @Data class FuncionalidadeDTO implements Serializable {

	private static final long serialVersionUID = 5911613757185877040L;

	@ApiModelProperty(value = "Código da Funcionalidade")
	private String id;

	@Size(max = 200)
	@ApiModelProperty(value = "Nome da Funcionalidade")
	private String nome;

	@Size(max = 40)
	@ApiModelProperty(value = "Código Mnemonico da Funcionalidade")
	private String mnemonico;

	@ApiModelProperty(value = "Código do Status do Usuário")
	private String idStatus;

	@ApiModelProperty(value = "Descrição do Status do Usuário")
	private String descricaoStatus;

	/**
	 * @return the id
	 */
	@JsonIgnore
	public Long getIdLong() {
		Long idLong = null;

		if (!Util.isEmpty(id)) {
			idLong = Long.parseLong(id);
		}
		return idLong;
	}
}
