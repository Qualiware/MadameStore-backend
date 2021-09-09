package br.ueg.modelo.application.repository.impl;

import br.ueg.modelo.application.dto.FiltroTipoAmigoDTO;
import br.ueg.modelo.application.model.TipoAmigo;
import br.ueg.modelo.application.repository.TipoAmigoRepositoryCustom;
import br.ueg.modelo.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
