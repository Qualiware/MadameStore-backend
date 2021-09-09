/*
 * SecurityConfig.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.application.configuration;

import br.ueg.modelo.api.config.ApiSecurityConfig;
import br.ueg.modelo.api.security.AuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Classe de configuração referente a segurança da aplicação.
 * 
 * @author UEG
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends ApiSecurityConfig {

	@Autowired
	private AuthenticationProvider authenticationProvider;

	/**
	 * @see ApiSecurityConfig#authenticationProvider()
	 */
	@Override
	protected AuthenticationProvider authenticationProvider() {
		return authenticationProvider;
	}
}
