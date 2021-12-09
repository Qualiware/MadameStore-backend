package br.ueg.madamestore.application.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.madamestore.application.dto.FiltroTipoProdutoDTO;
import br.ueg.madamestore.application.exception.SistemaMessageCode;
import br.ueg.madamestore.application.model.TipoProduto;
import br.ueg.madamestore.application.repository.TipoProdutoRepository;
import br.ueg.madamestore.comum.exception.BusinessException;
import br.ueg.madamestore.comum.util.CollectionUtil;
import br.ueg.madamestore.comum.util.Util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TipoProdutoService {

    @Autowired
    private TipoProdutoRepository tipoProdutoRepository;

    /**
     * Retorna uma lista de {@link TipoProduto} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<TipoProduto> getTipoProdutosByFiltro(final FiltroTipoProdutoDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<TipoProduto> grupos = tipoProdutoRepository.findAllByFiltro(filtroDTO);

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
    private void validarCamposObrigatoriosFiltro(final FiltroTipoProdutoDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getNome())) {
            vazio = Boolean.FALSE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }

    /**
     * Retorna uma lista de {@link TipoProduto} cadatrados .
     *
     * @return
     */
    public List<TipoProduto> getTodos() {
        List<TipoProduto> tipoProdutos = tipoProdutoRepository.findAll();

        if (CollectionUtil.isEmpty(tipoProdutos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return tipoProdutos;
    }

    /**
     * Retorno um a {@link TipoProduto} pelo Id informado.
     * @param id - id to tipo Produto
     * @return
     */
    public TipoProduto getById(Long id){
        Optional<TipoProduto> tipoProdutoOptional = tipoProdutoRepository.findById(id);

        if(!tipoProdutoOptional.isPresent()){
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return tipoProdutoOptional.get();
    }

    /**
     * Salva a instânica de {@link TipoProduto} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param tipoProduto
     * @return
     */
    public TipoProduto salvar(TipoProduto tipoProduto) {
        validarCamposObrigatorios(tipoProduto);
        validarDuplicidade(tipoProduto);

        TipoProduto grupoSaved = tipoProdutoRepository.save(tipoProduto);
        return grupoSaved;
    }

    public TipoProduto remover(Long id){
        TipoProduto tipoProduto = this.getById(id);

        tipoProdutoRepository.delete(tipoProduto);

        return tipoProduto;
    }



    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param tipoProduto
     */
    private void validarCamposObrigatorios(final TipoProduto tipoProduto) {
        boolean vazio = Boolean.FALSE;

        if (Util.isEmpty(tipoProduto.getNome())) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

    /**
     * Verifica se o TipoProduto a ser salvo já existe na base de dados.
     *
     * @param tipoProduto
     */
    private void validarDuplicidade(final TipoProduto tipoProduto) {
        Long count = tipoProdutoRepository.countByNomeAndNotId(tipoProduto.getNome(), tipoProduto.getId());

        if (count > BigDecimal.ZERO.longValue()) {
            throw new BusinessException(SistemaMessageCode.ERRO_TIPO_AMIGO_DUPLICADO);
        }
    }

}
