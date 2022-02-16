/*
 * TelefoneUsuarioDTO.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.madamestore.application.dto;

import br.ueg.madamestore.application.model.Mensagem;
import br.ueg.madamestore.application.model.TelefoneUsuario;
import br.ueg.madamestore.comum.util.Util;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe de transferência referente a entidade {@link Mensagem}.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Telefone")
public @Data class MensagemDTO implements Serializable {


	@ApiModelProperty(value = "Código da mensagem")
	private String id;

	@ApiModelProperty(value = "Código do Tipo do Telefone")
	private Long tipo;



	//@ApiModelProperty(value = "Descrição do Tipo do Telefone")
	//private String descricaoTipo;


	@ApiModelProperty(value = "Quantidade de retirada ou entrada")
	private String quantidade;

	@ApiModelProperty(value = "Data de alteracao")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private LocalDate dataAlteracao;

	@ApiModelProperty(value = "Código do produto")
	private String idProduto;

	@ApiModelProperty(value = "Nome do produto")
	private String nomeProduto;
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
