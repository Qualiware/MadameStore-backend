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
@ApiModel(value = "Entidade de transferência de Amigo")
public @Data
class AmigoDTO implements Serializable {

    @ApiModelProperty(value = "id do Amigo")
    private Long id;

    @ApiModelProperty(value = "Nome do Amiogo")
    private String nome;

    @ApiModelProperty(value = "Id do Tipo de Amigo")
    private Long idTipoAmigo;

    @ApiModelProperty(value = "nome do Tipo de Amigo")
    private String nomeTipoAmigo;

    @ApiModelProperty(value = "Data do Inicio da Amizade")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate dataAmizade;

    @ApiModelProperty(value = "Indica se o Amigo ainda é meu amigo")
    private Boolean amigo;

}
