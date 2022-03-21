/*
 * Usuario.java
 * Copyright (c) UEG.
 */
package br.ueg.madamestore.application.model;

import br.ueg.madamestore.application.configuration.Constante;
import br.ueg.madamestore.application.enums.StatusAtivoInativo;
import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.enums.TipoRetirada;
import br.ueg.madamestore.application.enums.TipoTelefoneUsuario;
import br.ueg.madamestore.application.enums.converter.StatusAtivoInativoConverter;
import br.ueg.madamestore.application.enums.converter.StatusSimNaoConverter;
import br.ueg.madamestore.application.enums.converter.TipoRetiradaConverter;
import br.ueg.madamestore.application.enums.converter.TipoTelefoneUsuarioConverter;
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
 *
 * Alterar Modelo para Movimentação
 */
@Entity
// Indica qual a tabela de versionamento será utilzada, opcional se se utilizar o padrão
@Table(name = "TBL_MENSAGEM", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name = "TBL_S_MENSAGEM", sequenceName = "TBL_S_MENSAGEM", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data class Mensagem implements Serializable{


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_MENSAGEM")
	@Column(name = "ID_MENSAGEM", nullable = false)
	private Long id;

	@Column(name = "DATA_Alteracao", nullable = false)
	private LocalDate dataAlteracao;

	@Column(name = "QUANTIDADE", nullable = false)
	private Integer quantidade;

	@Column(name = "DESCRICAO")
	private String descricaoMensagem;

	@Convert(converter = TipoRetiradaConverter.class)
	@Column(name = "TIPO", length = 1)
	private TipoRetirada tipo;

	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID_PRODUTO", nullable = false)
	private Produto produto;




}
