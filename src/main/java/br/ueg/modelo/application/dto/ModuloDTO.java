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
import br.ueg.modelo.application.model.Modulo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Classe de transferência referente a entidade {@link Modulo}.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Modulos do sistema")
public @Data class ModuloDTO implements Serializable {

	private static final long serialVersionUID = -3191642221341828960L;

	@ApiModelProperty(value = "Código do Modulo do sistema")
	private String id;

	@Size(max = 200)
	@ApiModelProperty(value = "Nome do Modulo do sistema")
	private String nome;

	@Size(max = 40)
	@ApiModelProperty(value = "Código Mnemonico da Funcionalidade")
	private String mnemonico;

	@ApiModelProperty(value = "Código do Status do Usuário")
	private String idStatus;

	@ApiModelProperty(value = "Descrição do Status do Usuário")
	private String descricaoStatus;

	@ApiModelProperty(value = "Lista de Funcionalidades do Módulo")
	private List<FuncionalidadeDTO> funcionalidades;

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
