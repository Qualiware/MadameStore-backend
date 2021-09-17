package br.ueg.madamestore.application.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ueg.madamestore.application.dto.FiltroAmigoDTO;
import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.model.Amigo;
import br.ueg.madamestore.application.repository.AmigoRepositoryCustom;
import br.ueg.madamestore.comum.util.Util;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AmigoRepositoryImpl implements AmigoRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Amigo> findAllByFiltro(FiltroAmigoDTO filtroAmigoDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT amigo FROM Amigo amigo");
        jpql.append(" INNER JOIN FETCH amigo.tipo tipo");

        jpql.append(" WHERE 1=1 ");

        if (!Util.isEmpty(filtroAmigoDTO.getNome())) {
            jpql.append(" AND UPPER(amigo.nome) LIKE UPPER('%' || :nome || '%')  ");
            parametros.put("nome", filtroAmigoDTO.getNome());
        }

        if (filtroAmigoDTO.getIdTipoAmigo()!=null) {
            jpql.append(" AND amigo.tipo.id = :idTipoAmigo ");
            parametros.put("idTipoAmigo", filtroAmigoDTO.getIdTipoAmigo());
        }

        if (filtroAmigoDTO.getDataAmizade() != null) {
            jpql.append(" AND amigo.dataAmizade > :dataAmizade ");
            parametros.put("dataAmizade", filtroAmigoDTO.getDataAmizade());
        }

        if (filtroAmigoDTO.getAmigo() != null) {
            jpql.append(" AND amigo.amigo = :amigo ");
            parametros.put("amigo", filtroAmigoDTO.getAmigo()? StatusSimNao.SIM:StatusSimNao.NAO);
        }

        TypedQuery<Amigo> query = entityManager.createQuery(jpql.toString(), Amigo.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
