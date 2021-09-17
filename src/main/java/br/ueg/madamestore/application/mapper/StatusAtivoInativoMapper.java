package br.ueg.madamestore.application.mapper;


import org.mapstruct.Mapper;

import br.ueg.madamestore.application.enums.StatusAtivoInativo;

@Mapper(componentModel = "spring")
public class StatusAtivoInativoMapper {
    public StatusAtivoInativo asStatusSimNao(boolean valor){
        return StatusAtivoInativo.getById(valor);
    }

    public boolean asBoolean(String valor){
        if(valor == null){
            return false;
        }
        return valor.equalsIgnoreCase(StatusAtivoInativo.ATIVO.toString());
    }
}
