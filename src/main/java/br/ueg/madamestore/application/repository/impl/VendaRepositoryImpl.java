package br.ueg.madamestore.application.repository.impl;

import br.ueg.madamestore.application.dto.FiltroProdutoDTO;
import br.ueg.madamestore.application.dto.FiltroVendaDTO;
import br.ueg.madamestore.application.model.Produto;
import br.ueg.madamestore.application.model.Venda;
import br.ueg.madamestore.application.repository.VendaRepositoryCustom;
import br.ueg.madamestore.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VendaRepositoryImpl implements VendaRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Venda> findAllByFiltro(FiltroVendaDTO filtroVendaDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT venda FROM Venda venda");
        jpql.append(" INNER JOIN FETCH venda.itemVenda itemVenda");
        jpql.append(" INNER JOIN FETCH venda.cliente cliente");

        jpql.append(" WHERE 1=1 ");


        if (filtroVendaDTO.getIdVenda()!=null) {
            jpql.append(" AND venda.produto.id = :idProduto ");
            parametros.put("idProduto", filtroVendaDTO.getIdVenda());
        }

        if (filtroVendaDTO.getValorTotal() != null) {
            jpql.append(" AND venda.valorTotal = :valorTotal ");
            parametros.put("valorTotal", filtroVendaDTO.getValorTotal());
        }


        if (filtroVendaDTO.getDataVenda() != null) {
            jpql.append(" AND venda.dataVenda > :dataVenda ");
            parametros.put("dataVenda", filtroVendaDTO.getDataVenda());
        }




        TypedQuery<Venda> query = entityManager.createQuery(jpql.toString(), Venda.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
