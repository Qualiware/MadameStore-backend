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
@ApiModel(value = "Dados do filtro de pesquisa de Venda")
public @Data class FiltroVendaDTO implements Serializable {

    @ApiModelProperty(value = "Id da Venda")
    private Long idVenda;

    @ApiModelProperty(value = "Valor Total")
    private Double valorTotal;


    @ApiModelProperty(value = "Data Venda")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate dataVenda;


    @ApiModelProperty(value = "Indica se o Amigo ainda é meu amigo")
    private Boolean statusVendido;

    @ApiModelProperty(value = "Indica se o Amigo ainda é meu amigo")
    private Boolean statusEspera;


}
