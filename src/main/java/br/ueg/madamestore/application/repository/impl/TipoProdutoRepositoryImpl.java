package br.ueg.madamestore.application.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ueg.madamestore.application.dto.FiltroTipoProdutoDTO;
import br.ueg.madamestore.application.model.TipoProduto;
import br.ueg.madamestore.application.repository.TipoProdutoRepositoryCustom;
import br.ueg.madamestore.comum.util.Util;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TipoProdutoRepositoryImpl implements TipoProdutoRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<TipoProduto> findAllByFiltro(FiltroTipoProdutoDTO filtroTipoProdutoDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT tipoProduto FROM TipoProduto tipoProduto");

        jpql.append(" WHERE 1=1 ");

        if (!Util.isEmpty(filtroTipoProdutoDTO.getNome())) {
            jpql.append(" AND UPPER(tipoProduto.nome) LIKE UPPER('%' || :nome || '%')  ");
            parametros.put("nome", filtroTipoProdutoDTO.getNome());
        }

        TypedQuery<TipoProduto> query = entityManager.createQuery(jpql.toString(), TipoProduto.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
