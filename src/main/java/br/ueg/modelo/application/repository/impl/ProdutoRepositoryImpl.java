package br.ueg.modelo.application.repository.impl;

import br.ueg.modelo.application.dto.FiltroAmigoDTO;
import br.ueg.modelo.application.dto.FiltroProdutoDTO;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.model.Amigo;
import br.ueg.modelo.application.model.Produto;
import br.ueg.modelo.application.repository.AmigoRepositoryCustom;
import br.ueg.modelo.application.repository.ProdutoRepositoryCustom;
import br.ueg.modelo.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Produto> findAllByFiltro(FiltroProdutoDTO filtroProdutoDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT produto FROM Produto produto");
        jpql.append(" INNER JOIN FETCH produto.tipo tipo");

        jpql.append(" WHERE 1=1 ");

        if (!Util.isEmpty(filtroProdutoDTO.getNome())) {
            jpql.append(" AND UPPER(produto.nome) LIKE UPPER('%' || :nome || '%')  ");
            parametros.put("nome", filtroProdutoDTO.getNome());
        }

        if (filtroProdutoDTO.getIdTipoProduto()!=null) {
            jpql.append(" AND produto.tipo.id = :idTipoProduto ");
            parametros.put("idTipoProduto", filtroProdutoDTO.getIdTipoProduto());
        }

        if (filtroProdutoDTO.getPreco() != null) {
            jpql.append(" AND produto.preco = :preco ");
            parametros.put("preco", filtroProdutoDTO.getPreco());
        }


        if (filtroProdutoDTO.getDataCadastro() != null) {
            jpql.append(" AND produto.dataCadastro > :dataCadastro ");
            parametros.put("dataCadastro", filtroProdutoDTO.getDataCadastro());
        }

        if (filtroProdutoDTO.getQuantidade() != null) {
            jpql.append(" AND produto.quantidade = :quantidade ");
            parametros.put("quantidade", filtroProdutoDTO.getQuantidade());
        }


        TypedQuery<Produto> query = entityManager.createQuery(jpql.toString(), Produto.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
