package br.ueg.madamestore.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.madamestore.application.dto.FiltroTipoAmigoDTO;
import br.ueg.madamestore.application.exception.SistemaMessageCode;
import br.ueg.madamestore.application.model.TipoAmigo;
import br.ueg.madamestore.application.repository.TipoAmigoRepository;
import br.ueg.madamestore.comum.exception.BusinessException;
import br.ueg.madamestore.comum.util.CollectionUtil;
import br.ueg.madamestore.comum.util.Util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TipoAmigoService {

    @Autowired
    private TipoAmigoRepository tipoAmigoRepository;

    /**
     * Retorna uma lista de {@link TipoAmigo} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<TipoAmigo> getTipoAmigosByFiltro(final FiltroTipoAmigoDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<TipoAmigo> grupos = tipoAmigoRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(grupos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return grupos;
    }

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Grupo, verifica se tem pelo meno 4 caracteres.
     *
     * @param filtroDTO
     */
    private void validarCamposObrigatoriosFiltro(final FiltroTipoAmigoDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getNome())) {
            vazio = Boolean.FALSE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }

    /**
     * Retorna uma lista de {@link TipoAmigo} cadatrados .
     *
     * @return
     */
    public List<TipoAmigo> getTodos() {
        List<TipoAmigo> tipoAmigos = tipoAmigoRepository.findAll();

        if (CollectionUtil.isEmpty(tipoAmigos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return tipoAmigos;
    }

    /**
     * Retorno um a {@link TipoAmigo} pelo Id informado.
     * @param id - id to tipo Amigo
     * @return
     */
    public TipoAmigo getById(Long id){
        Optional<TipoAmigo> tipoAmigoOptional = tipoAmigoRepository.findById(id);

        if(!tipoAmigoOptional.isPresent()){
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return tipoAmigoOptional.get();
    }

    /**
     * Salva a instânica de {@link TipoAmigo} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param tipoAmigo
     * @return
     */
    public TipoAmigo salvar(TipoAmigo tipoAmigo) {
        validarCamposObrigatorios(tipoAmigo);
        validarDuplicidade(tipoAmigo);

        TipoAmigo grupoSaved = tipoAmigoRepository.save(tipoAmigo);
        return grupoSaved;
    }

    public TipoAmigo remover(Long id){
        TipoAmigo tipoAmigo = this.getById(id);

        tipoAmigoRepository.delete(tipoAmigo);

        return tipoAmigo;
    }



    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param tipoAmigo
     */
    private void validarCamposObrigatorios(final TipoAmigo tipoAmigo) {
        boolean vazio = Boolean.FALSE;

        if (Util.isEmpty(tipoAmigo.getNome())) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

    /**
     * Verifica se o TipoAmigo a ser salvo já existe na base de dados.
     *
     * @param tipoAmigo
     */
    private void validarDuplicidade(final TipoAmigo tipoAmigo) {
        Long count = tipoAmigoRepository.countByNomeAndNotId(tipoAmigo.getNome(), tipoAmigo.getId());

        if (count > BigDecimal.ZERO.longValue()) {
            throw new BusinessException(SistemaMessageCode.ERRO_TIPO_AMIGO_DUPLICADO);
        }
    }

}
