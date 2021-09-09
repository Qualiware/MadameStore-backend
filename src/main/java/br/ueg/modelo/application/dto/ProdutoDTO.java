package br.ueg.modelo.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Produto")
public @Data
class ProdutoDTO implements Serializable {

    @ApiModelProperty(value = "id do Produto")
    private Long id;

    @ApiModelProperty(value = "Nome do Produto")
    private String nome;

    @ApiModelProperty(value = "Id do Tipo de Produto")
    private Long idTipoProduto;

    @ApiModelProperty(value = "nome do Tipo de Produto")
    private String nomeTipoProduto;

    @ApiModelProperty(value = "Preço")
    private Double preco;

    @ApiModelProperty(value = "Data de Cadastro")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate dataCadastro;



    @ApiModelProperty(value = "Quantidade")
    private Integer quantidade;

    @ApiModelProperty(value = "Valor Liquido")
    private Integer vliquido;

    @ApiModelProperty(value = "descricao")
    private String descricao;

    @ApiModelProperty(value = "Quantidade Vendida")
    private Integer quantidadeVendida;

}
