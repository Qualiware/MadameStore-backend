package br.ueg.madamestore.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Dados do filtro de pesquisa do Prouto")
public @Data class FiltroProdutoDTO implements Serializable {
    @ApiModelProperty(value = "Nome do Produto")
    private String nome;

    @ApiModelProperty(value = "Id do Tipo do Produto")
    private Long idTipoProduto;

    @ApiModelProperty(value = "PRECO DO PRODUTO")
    private Double preco;


    @ApiModelProperty(value = "Data do Cadastro do Produto")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate dataCadastro;


    @ApiModelProperty(value = "Quantidade do Produto")
    private Integer quantidade;

}
