package br.ueg.madamestore.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.madamestore.application.dto.FiltroAmigoDTO;
import br.ueg.madamestore.application.dto.FiltroProdutoDTO;
import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.exception.SistemaMessageCode;
import br.ueg.madamestore.application.model.Amigo;
import br.ueg.madamestore.application.model.Produto;
import br.ueg.madamestore.application.repository.AmigoRepository;
import br.ueg.madamestore.application.repository.ProdutoRepository;
import br.ueg.madamestore.comum.exception.BusinessException;
import br.ueg.madamestore.comum.util.CollectionUtil;
import br.ueg.madamestore.comum.util.Util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtosRepository;

    /**
     * Retorna uma lista de {@link Produto} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Produto> getProdutosByFiltro(final FiltroProdutoDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Produto> produtos = produtosRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(produtos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return produtos;
    }

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Produto
     *
     * @param filtroDTO
     */
    private void validarCamposObrigatoriosFiltro(final FiltroProdutoDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getNome())) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getDataCadastro()!=null) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getIdTipoProduto()!=null) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getQuantidade()!=null) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getPreco()!=null) {
            vazio = Boolean.FALSE;
        }



        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }

    /**
     * Retorna uma lista de {@link Produto} cadatrados .
     *
     * @return
     */
    public List<Produto> getTodos() {
        List<Produto> produtos = produtosRepository.getTodos() ;

        if (CollectionUtil.isEmpty(produtos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return produtos;
    }

    /**
     * Retorno um a {@link Produto} pelo Id informado.
     * @param id - id to Produto
     * @return
     */
    public Produto getById(Long id){
        Optional<Produto> produtoOptional = produtosRepository.findByIdFetch(id);

        if(!produtoOptional.isPresent()){
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return produtoOptional.get();
    }

    /**
     * Salva a instânica de {@link Produto} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param produto
     * @return
     */
    public Produto salvar(Produto produto) {



        validarCamposObrigatorios(produto);
        validarDuplicidade(produto);

        produtosRepository.save(produto);

        Produto produtoSaved = this.getById(produto.getId());
        return produtoSaved;
    }

    public Produto remover(Long id){
        Produto produto = this.getById(id);

        produtosRepository.delete(produto);

        return produto;
    }



    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param produto
     */
    private void validarCamposObrigatorios(final Produto produto) {
        boolean vazio = Boolean.FALSE;

        if (Util.isEmpty(produto.getNome())) {
            vazio = Boolean.TRUE;
        }

        if (produto.getTipo()==null || produto.getTipo().getId()== null) {
            vazio = Boolean.TRUE;
        }

        if (produto.getPreco()==null) {
            vazio = Boolean.TRUE;
        }

        if (produto.getQuantidade()==null) {
            vazio = Boolean.TRUE;
        }

        if (produto.getDataCadastro()==null) {
            vazio = Boolean.TRUE;
        }

        if (produto.getVliquido()==null) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

    /**
     * Verifica se o TipoProduto a ser salvo já existe na base de dados.
     *
     * @param produto
     */
    private void validarDuplicidade(final Produto produto) {
        Long count = produtosRepository.countByNomeAndNotId(produto.getNome(), produto.getId());

        if (count > BigDecimal.ZERO.longValue()) {
            throw new BusinessException(SistemaMessageCode.ERRO_AMIGO_DUPLICADO);
        }
    }

    // ESTATISTICA
    public List<Produto> getAllDesc() {
        return produtosRepository.getAllProdutosDesc();
    }

}
