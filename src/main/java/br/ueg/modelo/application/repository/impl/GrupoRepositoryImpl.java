package br.ueg.modelo.application.repository.impl;

import br.ueg.modelo.application.dto.FiltroGrupoDTO;
import br.ueg.modelo.application.model.Grupo;
import br.ueg.modelo.application.repository.GrupoRepositoryCustom;
import br.ueg.modelo.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GrupoRepositoryImpl implements GrupoRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Grupo> findAllByFiltro(FiltroGrupoDTO filtroGrupoDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT grupo FROM Grupo grupo");
        jpql.append(" LEFT JOIN FETCH grupo.grupoFuncionalidades grupoFuncionalidades");
        jpql.append(" LEFT JOIN FETCH grupoFuncionalidades.funcionalidade funcionalidade ");
        jpql.append(" LEFT JOIN FETCH funcionalidade.modulo modulos ");

        jpql.append(" WHERE 1=1 ");

        if (!Util.isEmpty(filtroGrupoDTO.getNome())) {
            jpql.append(" AND UPPER(grupo.nome) LIKE UPPER('%' || :nome || '%')  ");
            parametros.put("nome", filtroGrupoDTO.getNome());
        }

        if (!Util.isEmpty(filtroGrupoDTO.getIdStatus())) {
            jpql.append(" AND grupo.status = :status ");
            parametros.put("status", filtroGrupoDTO.getIdStatusEnum());
        }


        if (filtroGrupoDTO.getIdModulo() != null) {
            jpql.append(" AND modulos.id = :idModulo ");
            parametros.put("idModulo", filtroGrupoDTO.getIdModulo());
        }

        TypedQuery<Grupo> query = entityManager.createQuery(jpql.toString(), Grupo.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }

    @Override
    public List<Grupo> getGruposAtivosByIdSistema(Long idSistema) {
        return null;
    }
}
