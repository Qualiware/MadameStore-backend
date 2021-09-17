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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TBL_ITEM_VENDA", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "TBL_S_ITEM_VENDA", sequenceName = "TBL_S_ITEM_VENDA", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data class ItemVenda implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_ITEM_VENDA")
	@Column(name = "ID_ITEM_VENDA", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_VENDA", referencedColumnName = "ID_VENDA", nullable = false)
	private Venda venda;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID_PRODUTO", nullable = false)
	private Produto produto;
}
