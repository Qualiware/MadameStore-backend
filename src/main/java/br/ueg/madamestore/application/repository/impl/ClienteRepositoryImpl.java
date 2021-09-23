package br.ueg.madamestore.application.repository.impl;

import br.ueg.madamestore.application.dto.FiltroClienteDTO;
import br.ueg.madamestore.application.model.Cliente;
import br.ueg.madamestore.application.repository.ClienteRepositoryCustom;
import br.ueg.madamestore.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ClienteRepositoryImpl implements ClienteRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Cliente> findAllByFiltro(FiltroClienteDTO filtroClienteDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT cliente FROM Cliente cliente ");

        if (!Util.isEmpty(filtroClienteDTO.getNome())) {
			jpql.append(" WHERE UPPER(cliente.nome) LIKE UPPER('%' || :nome || '%') ");
			parametros.put("nome", filtroClienteDTO.getNome());
		}


        if (filtroClienteDTO.getDataCadastro() != null) {
            jpql.append(" WHERE cliente.dataCadastro > :dataCadastro ");
            parametros.put("dataCadastro", filtroClienteDTO.getDataCadastro());
        }

        if (filtroClienteDTO.getQuantidade() != null) {
            jpql.append(" WHERE cliente.quantidade = :quantidade ");
            parametros.put("quantidade", filtroClienteDTO.getQuantidade());
        }


        TypedQuery<Cliente> query = entityManager.createQuery(jpql.toString(), Cliente.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
