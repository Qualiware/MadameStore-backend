/*
 * TelefoneUsuarioDTO.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.modelo.application.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.ueg.modelo.comum.util.Util;
import br.ueg.modelo.application.model.TelefoneUsuario;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de transferência referente a entidade {@link TelefoneUsuario}.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Telefone")
public @Data class TelefoneUsuarioDTO implements Serializable {

	private static final long serialVersionUID = -2194758975061682611L;

	@ApiModelProperty(value = "Código do Telefone")
	private String id;

	@ApiModelProperty(value = "Código do Usuário")
	private String idUsuario;

	@Size(max = 11)
	@ApiModelProperty(value = "Número do Telefone")
	private String numero;

	@ApiModelProperty(value = "Código do Tipo do Telefone")
	private Long idTipo;

	@ApiModelProperty(value = "Descrição do Tipo do Telefone")
	private String descricaoTipo;

	@Size(max = 5)
	@ApiModelProperty(value = "DDD do Telefone")
	private String ddd;

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
