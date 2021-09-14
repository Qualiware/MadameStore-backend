package br.ueg.madamestore.application.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ueg.madamestore.application.dto.FiltroTipoAmigoDTO;
import br.ueg.madamestore.application.model.TipoAmigo;
import br.ueg.madamestore.application.repository.TipoAmigoRepositoryCustom;
import br.ueg.madamestore.comum.util.Util;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TipoAmigoRepositoryImpl implements TipoAmigoRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<TipoAmigo> findAllByFiltro(FiltroTipoAmigoDTO filtroTipoAmigoDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT tipoAmigo FROM TipoAmigo tipoAmigo");

        jpql.append(" WHERE 1=1 ");

        if (!Util.isEmpty(filtroTipoAmigoDTO.getNome())) {
            jpql.append(" AND UPPER(tipoAmigo.nome) LIKE UPPER('%' || :nome || '%')  ");
            parametros.put("nome", filtroTipoAmigoDTO.getNome());
        }

        TypedQuery<TipoAmigo> query = entityManager.createQuery(jpql.toString(), TipoAmigo.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
