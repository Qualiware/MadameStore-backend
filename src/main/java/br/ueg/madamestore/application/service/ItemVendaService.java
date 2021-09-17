package br.ueg.madamestore.application.service;

import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.application.model.Produto;
import br.ueg.madamestore.application.repository.ItemVendaRepository;
import br.ueg.madamestore.application.repository.UsuarioGrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ItemVendaService {
    @Autowired
    private ItemVendaRepository itemVendaRepository;

    /***
     * Retorna Grupos vinculados a um usuário específico
     * @param vendaId
     * @return
     */
    public List<Produto> getItensVenda(Long vendaId){
        return itemVendaRepository.findByIdVenda(vendaId);
    }

}
