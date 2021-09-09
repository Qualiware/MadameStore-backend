package br.ueg.modelo.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Dados do filtro de pesquisa de Amigo")
public @Data class FiltroAmigoDTO implements Serializable {
    @ApiModelProperty(value = "Nome do Amigo")
    private String nome;

    @ApiModelProperty(value = "Id do Tipo de Amigo")
    private Long idTipoAmigo;

    @ApiModelProperty(value = "Data do Inicio da Amizade")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate dataAmizade;

    @ApiModelProperty(value = "Indica se o Amigo ainda é meu amigo")
    private Boolean amigo;
}
