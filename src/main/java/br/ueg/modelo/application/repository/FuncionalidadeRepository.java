package br.ueg.modelo.application.repository;

import br.ueg.modelo.application.model.Funcionalidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionalidadeRepository extends JpaRepository<Funcionalidade, Long> {
}
