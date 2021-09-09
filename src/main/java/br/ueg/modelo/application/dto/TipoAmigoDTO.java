package br.ueg.modelo.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Tipo Amigo")
public @Data
class TipoAmigoDTO  implements Serializable {

    @ApiModelProperty(value = "id do Tipo Amigo")
    private Long id;

    @ApiModelProperty(value = "Nome do Tipo Amiogo")
    private String nome;

}
