package br.ueg.madamestore.application.mapper;


import br.ueg.madamestore.application.dto.ProdutoDTO;
import br.ueg.madamestore.application.dto.TipoProdutoDTO;
import br.ueg.madamestore.application.dto.UsuarioDTO;
import br.ueg.madamestore.application.dto.VendaDTO;
import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.model.Produto;
import br.ueg.madamestore.application.model.TipoProduto;
import br.ueg.madamestore.application.model.Usuario;
import br.ueg.madamestore.application.model.Venda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Classe adapter referente a entidade {@link Venda}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { ProdutoMapper.class, ItemVendaMapper.class,EsperaMapper.class, VendidoMapper.class})
public interface VendaMapper {

    /**
     * Converte a entidade {@link Venda} em DTO {@link VendaDTO}
     *
     * @param venda
     * @return
     */

    @Mapping(source = "cliente.id", target = "idCliente")
    @Mapping(source = "cliente.nome", target = "nomeCliente")
    public VendaDTO toDTO(Venda venda);

    /**
     * Converte o DTO {@link VendaDTO} para entidade {@link Venda}
     *
     * @param vendaDTO
     * @return
     */
    @Mapping(source = "vendaDTO.idCliente", target = "cliente.id")
    public Venda toEntity(VendaDTO vendaDTO);


}
