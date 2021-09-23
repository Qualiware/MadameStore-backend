/*
 * UsuarioTO.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.madamestore.application.dto;

import br.ueg.madamestore.application.model.UsuarioGrupo;
import br.ueg.madamestore.comum.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Classe de transferência referente a entidade {@link UsuarioGrupo}.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Usuario Grupos")
public @Data class ItemVendaDTO implements Serializable {

	@ApiModelProperty(value = "Código do item venda")
	private String id;

	@ApiModelProperty(value = "Código do venda")
	private String idVenda;

	@ApiModelProperty(value = "Código do produto")
	private String idProduto;

	@ApiModelProperty(value = "Nome do produto")
	private String nomeProduto;

	@ApiModelProperty(value = "preco do produto")
	private Double preco;


	@ApiModelProperty(value = "quantidade do produto")
	private String quantidade;

	@ApiModelProperty(value = "quantidade do produto vendida")
	private String quantidadeVendida;


	@ApiModelProperty(value = "valor liquido do produto")
	private String vliquido;



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
