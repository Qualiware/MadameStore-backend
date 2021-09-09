package br.ueg.modelo.application.service;

import br.ueg.modelo.application.dto.FiltroAmigoDTO;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.exception.SistemaMessageCode;
import br.ueg.modelo.application.model.Amigo;
import br.ueg.modelo.application.repository.AmigoRepository;
import br.ueg.modelo.comum.exception.BusinessException;
import br.ueg.modelo.comum.util.CollectionUtil;
import br.ueg.modelo.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AmigoService {

    @Autowired
    private AmigoRepository amigoRepository;

    /**
     * Retorna uma lista de {@link Amigo} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Amigo> getAmigosByFiltro(final FiltroAmigoDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Amigo> amigos = amigoRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(amigos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return amigos;
    }

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Amigo
     *
     * @param filtroDTO
     */
    private void validarCamposObrigatoriosFiltro(final FiltroAmigoDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getNome())) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getDataAmizade()!=null) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getIdTipoAmigo()!=null) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getAmigo()!=null) {
            vazio = Boolean.FALSE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }

    /**
     * Retorna uma lista de {@link Amigo} cadatrados .
     *
     * @return
     */
    public List<Amigo> getTodos() {
        List<Amigo> amigos = amigoRepository.getTodos() ;

        if (CollectionUtil.isEmpty(amigos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return amigos;
    }

    /**
     * Retorno um a {@link Amigo} pelo Id informado.
     * @param id - id to Amigo
     * @return
     */
    public Amigo getById(Long id){
        Optional<Amigo> amigoOptional = amigoRepository.findByIdFetch(id);

        if(!amigoOptional.isPresent()){
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return amigoOptional.get();
    }

    /**
     * Salva a instânica de {@link Amigo} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param amigo
     * @return
     */
    public Amigo salvar(Amigo amigo) {

        if(amigo.getId() == null && amigo.getAmigo() == null){
            amigo.setAmigo(StatusSimNao.SIM);
        }

        validarCamposObrigatorios(amigo);
        validarDuplicidade(amigo);

        amigoRepository.save(amigo);

        Amigo amigoSaved = this.getById(amigo.getId());
        return amigoSaved;
    }

    public Amigo remover(Long id){
        Amigo amigo = this.getById(id);

        amigoRepository.delete(amigo);

        return amigo;
    }



    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param amigo
     */
    private void validarCamposObrigatorios(final Amigo amigo) {
        boolean vazio = Boolean.FALSE;

        if (Util.isEmpty(amigo.getNome())) {
            vazio = Boolean.TRUE;
        }

        if (amigo.getTipo()==null || amigo.getTipo().getId()== null) {
            vazio = Boolean.TRUE;
        }

        if (amigo.getAmigo()==null) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

    /**
     * Verifica se o TipoAmigo a ser salvo já existe na base de dados.
     *
     * @param amigo
     */
    private void validarDuplicidade(final Amigo amigo) {
        Long count = amigoRepository.countByNomeAndNotId(amigo.getNome(), amigo.getId());

        if (count > BigDecimal.ZERO.longValue()) {
            throw new BusinessException(SistemaMessageCode.ERRO_AMIGO_DUPLICADO);
        }
    }

}
