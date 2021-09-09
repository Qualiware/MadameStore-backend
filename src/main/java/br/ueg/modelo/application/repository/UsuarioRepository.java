/*
 * UsuarioRepository.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.application.repository;

import br.ueg.modelo.application.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Classe de persistência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Repository
public interface UsuarioRepository extends UsuarioRepositoryCustom, JpaRepository<Usuario, Long> {
	// TODO Garantir que somente usuários com ID_ORIGEM_CADASTRO = PORTALSSO

	/**
	 * Retorna a instância do {@link Usuario} conforme o 'login' informado.
	 * 
	 * @param login
	 * @return
	 */
	public Usuario findByLogin(final String login);

	/**
	 * Retorna a instância do {@link Usuario} conforme o 'cpf' informado.
	 * 
	 * @param cpf
	 * @return
	 */
	public Usuario findByCpf(final String cpf);

	/**
	 * Retorna a instância do {@link Usuario} conforme o 'cpf' informado
	 * e que não tenha o 'id' informado.
	 * 
	 * @param cpf
	 * @param id
	 * @return
	 */
	@Query(" SELECT usuario FROM Usuario usuario "
			+ " WHERE usuario.cpf = :cpf "
			+ " AND (:id IS NULL OR usuario.id != :id)")
	public Usuario findByCpfAndNotId(@Param("cpf") final String cpf, @Param("id") final Long id);

	/**
	 * Retorna uma instância de {@link Usuario} conforme o 'id' informado.
	 * 
	 * @param id
	 * @return
	 */
	@Query(" SELECT usuario FROM Usuario usuario LEFT JOIN FETCH usuario.grupos usuarioGrupo "
			+ " LEFT JOIN FETCH usuarioGrupo.grupo grupo "
			+ " LEFT JOIN FETCH usuario.telefones telefone "
			+ " WHERE usuario.id = :id ")
	public Optional<Usuario> findByIdFetch(@Param("id") final Long id);


	/**
	 * Retorna o total de Usuários encontrados na base de dados conforme o cpf
	 * informado.
	 *
	 * @param cpf cpf do usuário
	 * @return
	 */
	public Long countByCpf(final String cpf);

}
