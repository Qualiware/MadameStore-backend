package br.ueg.madamestore.application.service;

import br.ueg.madamestore.application.dto.FiltroClienteDTO;
import br.ueg.madamestore.application.exception.SistemaMessageCode;
import br.ueg.madamestore.application.model.Cliente;
import br.ueg.madamestore.application.repository.ClienteRepository;
import br.ueg.madamestore.comum.exception.BusinessException;
import br.ueg.madamestore.comum.util.CollectionUtil;
import br.ueg.madamestore.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ClienteService {

    @Autowired
    private ClienteRepository clientesRepository;

    /**
     * Retorna uma lista de {@link Cliente} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Cliente> getClientesByFiltro(final FiltroClienteDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Cliente> clientes = clientesRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(clientes)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return clientes;
    }

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Cliente
     *
     * @param filtroDTO
     */
    private void validarCamposObrigatoriosFiltro(final FiltroClienteDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getNome())) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getDataCadastro()!=null) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getQuantidade()!=null) {
            vazio = Boolean.FALSE;
        }



        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }

    /**
     * Retorna uma lista de {@link Cliente} cadatrados .
     *
     * @return
     */
    public List<Cliente> getTodos() {
        List<Cliente> clientes = clientesRepository.getTodos() ;

        if (CollectionUtil.isEmpty(clientes)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return clientes;
    }

    /**
     * Retorno um a {@link Cliente} pelo Id informado.
     * @param id - id to Cliente
     * @return
     */
    public Cliente getById(Long id){
        Optional<Cliente> clienteOptional = clientesRepository.findByIdFetch(id);

        if(!clienteOptional.isPresent()){
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return clienteOptional.get();
    }

    /**
     * Salva a instânica de {@link Cliente} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param cliente
     * @return
     */
    public Cliente salvar(Cliente cliente) {



        validarCamposObrigatorios(cliente);
        validarDuplicidade(cliente);

        clientesRepository.save(cliente);

        Cliente clienteSaved = this.getById(cliente.getId());
        return clienteSaved;
    }

    public Cliente remover(Long id){
        Cliente cliente = this.getById(id);

        clientesRepository.delete(cliente);

        return cliente;
    }



    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param cliente
     */
    private void validarCamposObrigatorios(final Cliente cliente) {
        boolean vazio = Boolean.FALSE;

        if (Util.isEmpty(cliente.getNome())) {
            vazio = Boolean.TRUE;
        }

        if (cliente.getDataCadastro()==null) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

    /**
     * Verifica se o TipoCliente a ser salvo já existe na base de dados.
     *
     * @param cliente
     */
    private void validarDuplicidade(final Cliente cliente) {
        Long count = clientesRepository.countByNomeAndNotId(cliente.getNome(), cliente.getId());

        if (count > BigDecimal.ZERO.longValue()) {
            throw new BusinessException(SistemaMessageCode.ERRO_AMIGO_DUPLICADO);
        }
    }

}
