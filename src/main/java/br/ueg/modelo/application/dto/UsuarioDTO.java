/*
 * UsuarioDTO.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.modelo.application.dto;

import br.ueg.modelo.comum.util.Util;
import br.ueg.modelo.application.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Classe de transferência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Usuario")
public @Data class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = -3145730384721847808L;

	@ApiModelProperty(value = "Código do Usuário")
	private String id;

	@JsonFormat(shape = Shape.STRING)
	@ApiModelProperty(value = "Data da última atualização do cadastro do Usuário")
	private LocalDate dataAtualizado;

	@JsonFormat(shape = Shape.STRING)
	@ApiModelProperty(value = "Data do cadastro do Usuário")
	private LocalDate dataCadastrado;

	@Size(max = 75)
	@ApiModelProperty(value = "Email do usuário")
	private String email;

	@Size(max = 20)
	@ApiModelProperty(value = "Login do Usuário")
	private String login;

	@Size(max = 65)
	@ApiModelProperty(value = "Nome do Usuário")
	private String nome;

	@Size(max = 14)
	@ApiModelProperty(value = "Cpf do Usuário")
	private String cpf;

	@JsonFormat(shape = Shape.STRING)
	@ApiModelProperty(value = "Data de nascimento Usuário")
	private LocalDate dataNascimento;

	@ApiModelProperty(value = "Código do Status do Usuário")
	private boolean status;

	@ApiModelProperty(value = "Quantidade Tentativa de Acesso")
	private BigDecimal quantidadeTentativaAcesso;

	@JsonFormat(shape = Shape.STRING)
	@ApiModelProperty(value = "Data do útimo acesso do Usuário")
	private LocalDate ultimoAcesso;

	@JsonFormat(shape = Shape.STRING)
	@ApiModelProperty(value = "Data da expiração do acesso do Usuário")
	private LocalDate dataAcessoExpirado;

	@ApiModelProperty(value = "Acesso do Usuário Bloqueado")
	private boolean acessoBloqueado;

	@ApiModelProperty(value = "código - Acesso do Usuário Expirado")
	private boolean acessoExpirado;

	@ApiModelProperty(value = "Quantidade de Acessos do usuário")
	private BigDecimal quantidadeAcesso;

	@ApiModelProperty(value = "Grupos do Usuário")
	private List<UsuarioGrupoDTO> grupos;

	@ApiModelProperty(value = "Telefones do Usuário")
	private List<TelefoneUsuarioDTO> telefones;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private Long idUsuarioLogado;

	public UsuarioDTO(String id, String login) {
		this.id = id;
		this.login = login;
	}

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
