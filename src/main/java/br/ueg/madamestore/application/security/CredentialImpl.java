/*
 * CredentialImpl.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.security;

import java.util.List;

import br.ueg.madamestore.application.dto.CredencialDTO;
import br.ueg.madamestore.comum.security.Credential;

/**
 * Classe responsável por manter as informações da Credencial do Usuário logado.
 * 
 * @author UEG
 */
public class CredentialImpl implements Credential {

	private CredencialDTO credencialDTO;

	private String accessToken;

	/**
	 * Construtor privado para garantir o Singleton.
	 */
	private CredentialImpl() {

	}

	/**
	 * Construtor privado para garantir o Singleton.
	 * 
	 * @param credencialDTO
	 * @param accessToken
	 */
	private CredentialImpl(final CredencialDTO credencialDTO, final String accessToken) {
		this.accessToken = accessToken;
		this.credencialDTO = credencialDTO;
	}

	/**
	 * Fábrica de instâncias de {@link CredentialImpl}.
	 * 
	 * @param credencialDTO
	 * @param accessToken
	 * @return
	 */
	public static CredentialImpl newInstance(final CredencialDTO credencialDTO, final String accessToken) {
		return new CredentialImpl(credencialDTO, accessToken);
	}

	/**
	 * @see Credential#getAccessToken()
	 */
	@Override
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @see Credential#getUsername()
	 */
	@Override
	public String getName() {
		return credencialDTO != null ? credencialDTO.getNome() : null;
	}

	/**
	 * @see Credential#getRoles()
	 */
	@Override
	public List<String> getRoles() {
		return credencialDTO != null ? credencialDTO.getRoles() : null;
	}

	/**
	 * @return the idUsuario
	 */
	public Long getIdUsuario() {
		return credencialDTO != null ? credencialDTO.getId() : null;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return credencialDTO != null ? credencialDTO.getEmail() : null;
	}
}
