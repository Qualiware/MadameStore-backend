/*
 * UsuarioGrupo.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.madamestore.application.model;

import br.ueg.madamestore.application.configuration.Constante;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TBL_ITEM_VENDA", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "TBL_S_ITEM_VENDA", sequenceName = "TBL_S_ITEM_VENDA", allocationSize = 1, schema = Constante.DATABASE_OWNER)
@ToString
public @Data class ItemVenda implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_ITEM_VENDA")
	@Column(name = "ID_ITEM_VENDA", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_VENDA", referencedColumnName = "ID_VENDA", nullable = false)
	@ToStringExclude
	private Venda venda;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID_PRODUTO", nullable = false)
	private Produto produto;

	@Column(name="PRECOPRODUTO", nullable = true)
	private Double precoProduto;

	@Column(name="VALORLIQUIDO", nullable = true)
	private Double valorLiquido;

	@Column(name="TIPOPRODUTO", nullable = true)
	private String tipoProduto;


	@Column(name="QUANTIDADE", nullable = true)
	private Integer quantidadeVendida;
}
