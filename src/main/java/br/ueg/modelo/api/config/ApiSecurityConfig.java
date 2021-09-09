/*
 * SecurityConfig.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.api.config;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.ueg.modelo.api.security.AuthenticationProvider;
import br.ueg.modelo.api.security.CredentialProvider;
import br.ueg.modelo.api.security.JwtAuthenticationFilter;

/**
 * Classe de configuração referente a segurança da aplicação.
 * 
 * @author UEG
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public abstract class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Retorna a instância de {@link AuthenticationProvider} necessária na validação
	 * do 'Token JWT'.
	 * 
	 * @return
	 */
	@Bean
	protected abstract AuthenticationProvider authenticationProvider();

	/**
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().disable();
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/h2_console/**").permitAll();// libera h2 console
		http.headers().frameOptions().disable();// libera h2 console
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtAuthenticationFilter(), BasicAuthenticationFilter.class);
	}

	/**
	 * Retorna a instância de {@link JwtAuthenticationFilter}.
	 *
	 * @return
	 * @throws Exception
	 */
	private JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		return new JwtAuthenticationFilter(authenticationProvider());
	}

	/**
	 * Retorna a instância de {@link CredentialProvider}.
	 * 
	 * @return
	 */
	@Bean
	protected CredentialProvider credentialProvider() {
		return new CredentialProvider();
	}

}
