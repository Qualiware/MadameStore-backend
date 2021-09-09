/*
 * TelefoneUsuario.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.application.model;

import br.ueg.modelo.application.enums.TipoTelefoneUsuario;
import br.ueg.modelo.application.enums.converter.TipoTelefoneUsuarioConverter;
import br.ueg.modelo.application.configuration.Constante;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Classe de representação de 'Telefone'.
 *
 * @author UEG
 */
@Entity
@Table(name = "TBL_TELEFONE_USUARIO", schema= Constante.DATABASE_OWNER)
@EqualsAndHashCode(of = { "ddd", "numero" })
@SequenceGenerator(name = "TBL_S_TELEFONE_USUARIO", sequenceName = "TBL_S_TELEFONE_USUARIO", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data class TelefoneUsuario implements Serializable{

	private static final long serialVersionUID = -3928643077340896948L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_TELEFONE_USUARIO")
	@Column(name = "CODG_TELEFONE_USUARIO", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODG_USUARIO", referencedColumnName = "CODG_USUARIO", nullable = false)
	private Usuario usuario;

	@Column(name = "NUMR_TELEFONE_USUARIO", length = 11, nullable = false)
	private String numero;

	@Convert(converter = TipoTelefoneUsuarioConverter.class)
	@Column(name = "TIPO_TELEFONE_USUARIO", nullable = false, length = 1)
	private TipoTelefoneUsuario tipo;

	@Column(name = "DDD_TELEFONE_USUARIO", length = 5)
	private Long ddd;
}
