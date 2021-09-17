package br.ueg.madamestore.application.repository;

import br.ueg.madamestore.application.enums.StatusAtivoInativo;
import br.ueg.madamestore.application.model.ItemVenda;
import br.ueg.madamestore.application.model.Produto;
import br.ueg.madamestore.application.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

    ItemVenda findByVenda(Venda venda);

    @Query("select distinct g from ItemVenda ug " +
            "LEFT JOIN ug.venda u " +
            "LEFT JOIN ug.produto g " +
            "where ug.venda.id=:idVenda")
    List<Produto> findByIdVenda(Long idVenda);
}

