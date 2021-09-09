package br.ueg.modelo.application.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.ueg.modelo.comum.util.Util;
import br.ueg.modelo.application.enums.StatusAtivoInativo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de transferência referente aos dados filtro para Usuário.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de dados de filtro de Usuário")
public @Data class FiltroUsuarioDTO implements Serializable {

    private static final long serialVersionUID = 3180319002111253549L;

    @Size(max = 39)
    @ApiModelProperty(value = "Login do Usuário")
    private String login;

    @Size(max = 100)
    @ApiModelProperty(value = "Nome do Usuário")
    private String nome;

    @ApiModelProperty(value = "Código do Status do Usuário")
    private String idStatus;

    /**
     * @return the id
     */
    @JsonIgnore
    public StatusAtivoInativo getStatusEnum() {
    	StatusAtivoInativo status = null;

        if (!Util.isEmpty(this.idStatus)) {
            status = StatusAtivoInativo.getById(this.idStatus);
        }
        return status;
    }

}
