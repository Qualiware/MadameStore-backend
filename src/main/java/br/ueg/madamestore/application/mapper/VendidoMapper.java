package br.ueg.madamestore.application.mapper;


import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusVendido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class VendidoMapper {
    public StatusVendido asStatusSimNao(Boolean valor){
        return StatusVendido.getByIdStatusSimNao(valor);
    }

    public boolean asBoolean(StatusVendido valor){
        if(valor == null){
            return false;
        }
        return valor.toString().equalsIgnoreCase(StatusVendido.SIM.toString());
    }
}
