package br.ueg.madamestore.application.mapper;


import org.mapstruct.Mapper;

import br.ueg.madamestore.application.enums.StatusSimNao;

@Mapper(componentModel = "spring")
public class SimNaoMapper {
    public StatusSimNao asStatusSimNao(boolean valor){
        return StatusSimNao.getByIdStatusSimNao(valor);
    }

    public boolean asBoolean(StatusSimNao valor){
        if(valor == null){
            return false;
        }
        return valor.toString().equalsIgnoreCase(StatusSimNao.SIM.toString());
    }
}
