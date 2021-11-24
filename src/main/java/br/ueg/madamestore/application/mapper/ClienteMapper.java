package br.ueg.madamestore.application.mapper;


import br.ueg.madamestore.application.dto.ClienteDTO;
import br.ueg.madamestore.application.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Classe adapter referente a entidade {@link Cliente}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { ClienteDTO.class})
public interface ClienteMapper {
    /**
     * Converte a entidade {@link Cliente} em DTO {@link ClienteDTO}
     *
     * @param cliente
     * @return
     */

    public ClienteDTO toDTO(Cliente cliente);

    /**
     * Converte o DTO {@link TipoClienteDTO} para entidade {@link TipoCliente}
     *
     * @param clienteDTO
     * @return
     */

    public Cliente toEntity(ClienteDTO clienteDTO);
}
