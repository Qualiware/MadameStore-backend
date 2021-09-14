/* 	 
 * AuthTO.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.madamestore.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.ueg.madamestore.comum.util.Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Classe de transferência referente aos dados de autenticação.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de dados de Autenticação")
public @Data class AuthDTO implements Serializable {

	private static final long serialVersionUID = 5374096682432769206L;

	@Size(max = 8, message = "deve ser menor ou igual a 8")
	@ApiModelProperty(value = "Id do Usuáro", required = true)
	private String idUsuario;// TODO Verificar se não é mais necessário.

	@ApiModelProperty(value = "Email do Usuário", required = true)
	private String email;

	@ApiModelProperty(value = "Senha do Usuário", required = true)
	private String senha;

	@ApiModelProperty(value ="Teste validação", required = true)
	private Integer numero;

	/**
	 * @return the idUsuario
	 */
	@JsonIgnore
	@ApiModelProperty(hidden = true)
	public Long getIdUsuarioLong() {
		Long idLong = null;

		if (!Util.isEmpty(idUsuario)) {
			idLong = Long.parseLong(idUsuario);
		}
		return idLong;
	}
}
