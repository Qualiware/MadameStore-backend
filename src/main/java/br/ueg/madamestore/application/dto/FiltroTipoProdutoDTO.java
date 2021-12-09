package br.ueg.madamestore.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Dados do filtro de pesquisa de Tipo Produto")
public @Data class FiltroTipoProdutoDTO implements Serializable {
    @ApiModelProperty(value = "Nome do Produto")
    private String nome;
}
