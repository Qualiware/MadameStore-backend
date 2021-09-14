/*
 * Credential.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.comum.security;

import java.util.List;

/**
 * Interface de contrato referente a credencial padrão da UEG, para a
 * comunicação entre os diversos 'clients' de Microserviço.
 * 
 * @author UEG
 */
public interface Credential {

	/**
	 * Returns the username logged in.
	 * 
	 * @return -
	 */
	public String getName();

	/**
	 * Returns the e-mail logged in.
	 * 
	 * @return -
	 */
	public String getEmail();

	/**
	 * Returns the roles logged in.
	 * 
	 * @return -
	 */
	public List<String> getRoles();

	/**
	 * Returns the logged in user's access token.
	 * 
	 * @return -
	 */
	public String getAccessToken();

}
