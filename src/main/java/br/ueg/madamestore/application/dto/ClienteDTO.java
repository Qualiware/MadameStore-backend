package br.ueg.madamestore.application.dto;

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
@ApiModel(value = "Entidade de transferência de Cliente")
public @Data
class ClienteDTO implements Serializable {

    @ApiModelProperty(value = "id do Cliente")
    private Long id;

    @ApiModelProperty(value = "Nome do Cliente")
    private String nome;

    @ApiModelProperty(value = "Email do Cliente")
    private String email;

    @ApiModelProperty(value = "Telefone do Cliente")
    private String telefone;

    @ApiModelProperty(value = "Data de Cadastro")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate dataCadastro;


    @ApiModelProperty(value = "Quantidade")
    private Integer quantidade;

}
