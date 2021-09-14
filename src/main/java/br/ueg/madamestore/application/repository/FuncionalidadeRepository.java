package br.ueg.madamestore.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ueg.madamestore.application.model.Funcionalidade;

public interface FuncionalidadeRepository extends JpaRepository<Funcionalidade, Long> {
}
