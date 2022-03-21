package br.ueg.madamestore.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Tipo Produto")
public @Data
class TipoProdutoDTO implements Serializable {

    @ApiModelProperty(value = "id do Tipo Produto")
    private Long id;

    @ApiModelProperty(value = "Nome do Tipo Produto")
    private String nome;

    @ApiModelProperty(value= "Valor")
    private String valor;

    @ApiModelProperty(value= "Valor")
    private String tipoProduto;

}
