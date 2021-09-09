/* 	 
 * CredencialTO.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.modelo.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Classe de transferência responsável por representar a Credencial do Usuário.
 *
 * @author UEG
 */
@JsonInclude(Include.NON_NULL)
@ApiModel(value = "Representação de Credencial do Usuário")
public @Data class CredencialDTO implements Serializable {

	private static final long serialVersionUID = 7616722014159043532L;

	@ApiModelProperty(value = "Id do Usuário")
	private Long id;

	@ApiModelProperty(value = "Nome do Usuário")
	private String nome;

	@ApiModelProperty(value = "Login do Usuário")
	private String login;

	@ApiModelProperty(value = "Email do Usário")
	private String email;

	@ApiModelProperty(value = "Lista de permissões do Usuário")
	private List<String> roles;

	@ApiModelProperty(value = "Token de acesso")
	private String accessToken;

	@ApiModelProperty(value = "Tempo de expiração do token de acesso")
	private Long expiresIn;

	@ApiModelProperty(value = "Token de refresh")
	private String refreshToken;

	@ApiModelProperty(value = "Tempo de expiração do token de refresh")
	private Long refreshExpiresIn;


}
