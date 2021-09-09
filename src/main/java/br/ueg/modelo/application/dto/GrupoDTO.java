/*
 * UsuarioTO.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.modelo.application.dto;

import br.ueg.modelo.application.model.Grupo;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * Classe de transferência referente a entidade {@link Grupo}.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Grupo")
public @Data class GrupoDTO implements Serializable {

	private static final long serialVersionUID = -4907983765144204384L;

	@ApiModelProperty(value = "Código do Grupo de Usuários")
	private Long id;

	@Size(max = 50)
	@ApiModelProperty(value = "Nome do Grupo de usuários")
	private String nome;

	@Size(max = 200)
	@ApiModelProperty(value = "Descricao do Grupo de Usuários")
	private String descricao;

	@ApiModelProperty(value = "Código do Status do Grupo de Usuários")
	private boolean status;

	@ApiModelProperty(value = "indica se o Grupo de Usuários é de administradores")
	private boolean administrador;

	@ApiModelProperty(value = "Lista de Grupo Funcionalidades que o Grupo de usuário possui")
	private Set<GrupoFuncionalidadeDTO> grupoFuncionalidades;
}
