/* 	 
 * UsuarioSenhaTO.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.modelo.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Classe de transferência referente as alterações de senha do usuário.
 * 
 * @author UEG
 */

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Sistema")
public @Data class UsuarioSenhaDTO implements Serializable {

	private static final long serialVersionUID = 3198994205885488802L;

	public enum TipoRedefinicaoSenha {
		ativacao, recuperacao, alteracao
	}

	@ApiModelProperty(value = "E-mail do Usuário onde a solicitação de senha foi enviada. (Campo não será considerado como parâmetro de entrada)")
	private String email;

	@Size(min = 8, max = 20, message = "deve ser maior ou igual a 8 e menor ou igual a 20")
	@ApiModelProperty(value = "Senha Antiga")
	private String senhaAntiga;

	@ApiModelProperty(value = "Nova Senha")
	@Size(min = 8, max = 20, message = "deve ser maior ou igual a 8 e menor ou igual a 20")
	private String novaSenha;

	@ApiModelProperty(value = "Confirmar Senha")
	@Size(min = 8, max = 20, message = "deve ser maior ou igual a 8 e menor ou igual a 20")
	private String confirmarSenha;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private Long idUsuario;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private TipoRedefinicaoSenha tipo;

	/**
	 * @param email email do usuário
	 */
	public UsuarioSenhaDTO(String email){
		this.email = email;
	}

	/**
	 * Verifica se o Tipo é igual a 'ativacao'.
	 * 
	 * @return -
	 */
	@JsonIgnore
	@ApiModelProperty(hidden = true)
	public boolean isAtivacao() {
		return TipoRedefinicaoSenha.ativacao.equals(tipo);
	}

	/**
	 * Verifica se o Tipo é igual a 'recuperacao'.
	 * 
	 * @return -
	 */
	@JsonIgnore
	@ApiModelProperty(hidden = true)
	public boolean isRecuperacao() {
		return TipoRedefinicaoSenha.recuperacao.equals(tipo);
	}
}
