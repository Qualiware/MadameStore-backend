/*
 * Usuario.java
 * Copyright (c) UEG.
 */
package br.ueg.modelo.application.model;

import br.ueg.modelo.application.enums.StatusAtivoInativo;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.enums.converter.StatusAtivoInativoConverter;
import br.ueg.modelo.application.enums.converter.StatusSimNaoConverter;
import br.ueg.modelo.application.configuration.Constante;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * Classe de representação de 'Sistema'.
 *
 * @author UEG
 */
@Entity
// Indica qual a tabela de versionamento será utilzada, opcional se se utilizar o padrão
@Table(name = "TBL_USUARIO", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name = "TBL_S_USUARIO", sequenceName = "TBL_S_USUARIO", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data class Usuario implements Serializable{

	private static final long serialVersionUID = -8899975090870463525L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_USUARIO")
	@Column(name = "CODG_USUARIO", nullable = false)
	private Long id;

	@Column(name = "DATA_ATUALIZADO", nullable = false)
	private LocalDate dataAtualizado;

	@Column(name = "DATA_CADASTRADO", nullable = false)
	private LocalDate dataCadastrado;

	@Column(name = "EMAIL", length = 75, nullable = false)
	private String email;

	// https://docs.microsoft.com/pt-br/windows/win32/adschema/a-samaccountname?redirectedfrom=MSDN
	@Column(name = "LOGIN_USUARIO", length = 20, nullable = false)
	private String login;

	@Column(name = "SENHA_USUARIO", length = 255, nullable = false)
	private String senha;

	@Column(name = "NOME_USUARIO", length = 65, nullable = false)
	private String nome;

	@Column(name = "NUMR_CPF", length = 14, nullable = false)
	private String cpf;

	@Convert(converter = StatusAtivoInativoConverter.class)
	@Column(name = "STAT_USUARIO", nullable = false, length = 1)
	private StatusAtivoInativo status;

	@Convert(converter = StatusSimNaoConverter.class)
	@Column(name = "STAT_BLOQUEIO_ACESSO", length = 1)
	private StatusSimNao acessoBloqueado;

	@Column(name = "CONT_TENTATIVA_ACESSO", length = 1)
	private String quantidadeTentativaAcesso;

	@Column(name = "DATA_ULTIMO_ACESSO")
	private LocalDate ultimoAcesso;

	@Column(name = "DATA_EXPIRADO_ACESSO")
	private LocalDate dataAcessoExpirado;

	/**
	 * Quando o usuário fica mais de 90 dia sem acesso ele deve ficar acessoExpirado = {@link StatusSimNao}.SIM
	 */
	@Convert(converter = StatusSimNaoConverter.class)
	@Column(name = "STAT_EXPIRADO_ACESSO", length = 1)
	private StatusSimNao acessoExpirado;

	@Column(name = "QTDE_ACESSO", precision = 2)
	private BigDecimal quantidadeAcesso;

	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UsuarioGrupo> grupos;

	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<TelefoneUsuario> telefones;

	/**
	 * Verifica se o Status do Usuário é igual a 'Ativo'.
	 *
	 * @return -
	 */
	@Transient
	public boolean isStatusAtivo() {
		return status != null && StatusAtivoInativo.ATIVO.getId().equals(status.getId());
	}

}
