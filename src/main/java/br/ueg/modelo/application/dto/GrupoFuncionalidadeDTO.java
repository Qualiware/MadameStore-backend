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
import br.ueg.modelo.application.model.GrupoFuncionalidade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Classe de transferência referente a entidade {@link GrupoFuncionalidade}.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Grupo funcionalidades")
public @Data class GrupoFuncionalidadeDTO implements Serializable {

	private static final long serialVersionUID = -3858126875464908214L;

	@ApiModelProperty(value = "Código do Grupo Funcionalidade")
	private String id;

	@ApiModelProperty(value = "Código do Grupo")
	private String idGrupo;

	@ApiModelProperty(value = "Funcionalidade do Grupo")
	private FuncionalidadeDTO funcionalidade;



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
