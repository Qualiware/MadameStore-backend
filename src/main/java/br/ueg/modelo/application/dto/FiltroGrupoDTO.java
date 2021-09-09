package br.ueg.modelo.application.dto;

import br.ueg.modelo.comum.util.Util;
import br.ueg.modelo.application.enums.StatusAtivoInativo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Dados do filtro de pesquisa de Grupos")
public @Data class FiltroGrupoDTO implements Serializable {
    private static final long serialVersionUID = 7616722014159043532L;

    @ApiModelProperty(value = "Nome do Grupo")
    private String nome;

    @ApiModelProperty(value = "Status (A)tivo / (I)nativo")
    private String idStatus;

    @ApiModelProperty(value = "Identificação do Módulo")
    private Long idModulo;

    @ApiModelProperty(value = "Id usuario logado")
    private Long idUsuario;

    /**
     * @return the id
     */
    @JsonIgnore
    public StatusAtivoInativo getIdStatusEnum() {
        StatusAtivoInativo status = null;

        if (!Util.isEmpty(this.idStatus)) {
            status = StatusAtivoInativo.getById(this.idStatus);
        }
        return status;
    }



}
