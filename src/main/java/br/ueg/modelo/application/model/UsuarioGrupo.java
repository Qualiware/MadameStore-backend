/*
 * UsuarioGrupo.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.modelo.application.model;

import br.ueg.modelo.application.configuration.Constante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TBL_USUARIO_GRUPO", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "TBL_S_USUARIO_GRUPO", sequenceName = "TBL_S_USUARIO_GRUPO", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data class UsuarioGrupo implements Serializable {

	private static final long serialVersionUID = -8899975090870463525L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_USUARIO_GRUPO")
	@Column(name = "ID_USUARIO_GRUPO", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODG_USUARIO", referencedColumnName = "CODG_USUARIO", nullable = false)
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_GRUPO_USUARIO", referencedColumnName = "ID_GRUPO_USUARIO", nullable = false)
	private Grupo grupo;
}
