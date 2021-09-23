package br.ueg.madamestore.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Dados do filtro de pesquisa do Cliente")
public @Data class FiltroClienteDTO implements Serializable {
    @ApiModelProperty(value = "Nome do Cliente")
    private String nome;


    @ApiModelProperty(value = "Data do Cadastro do Cliente")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate dataCadastro;


    @ApiModelProperty(value = "Quantidade do Cliente")
    private Integer quantidade;

}
