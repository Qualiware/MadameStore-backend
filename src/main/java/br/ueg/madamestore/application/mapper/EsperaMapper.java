package br.ueg.madamestore.application.mapper;


import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusSimNao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class EsperaMapper {
    public StatusEspera asStatusSimNao(Boolean valor){
        return StatusEspera.getByIdStatusSimNao(valor);
    }

    public boolean asBoolean(StatusEspera valor){
        if(valor == null){
            return false;
        }
        return valor.toString().equalsIgnoreCase(StatusEspera.SIM.toString());
    }
}
