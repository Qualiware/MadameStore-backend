/*
 * JwtAuthenticationFilter.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.api.security;

import br.ueg.modelo.comum.security.Credential;
import br.ueg.modelo.comum.util.CollectionUtil;
import br.ueg.modelo.comum.util.Util;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Filtro que verifica as credenciais de autenticação do usuário.
 * 
 * @author UEG.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	public static final String AUTH_HEADER = "Authorization";
	public static final String BEARER_PREFIX = "Bearer ";

	private AuthenticationProvider authenticationProvider;

	/**
	 * Construtor da classe.
	 * 
	 * @param authenticationProvider
	 */
	public JwtAuthenticationFilter(AuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(final HttpServletRequest servletRequest, final HttpServletResponse servletResponse,
                                    FilterChain chain) throws ServletException, IOException {

		final String token = getAccessToken(servletRequest);

		if (!Util.isEmpty(token) && isTokenBearer(servletRequest)) {
			Credential credential = null;
			credential = authenticationProvider.getAuthentication(token);
			Authentication authentication = getAuthentication(credential);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(servletRequest, servletResponse);
	}

	/**
	 * Returns the instance {@link Authentication} by {@link Credential}.
	 * 
	 * @param credential
	 * @return
	 */
	private Authentication getAuthentication(final Credential credential) {
		Authentication authentication = null;

		if (credential == null) {
			authentication = new UsernamePasswordAuthenticationToken(null, null);
		} else {
			List<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(credential);
			authentication = new UsernamePasswordAuthenticationToken(credential.getUsername(), credential,
					grantedAuthorities);
		}
		return authentication;
	}

	/**
	 * Returns the list of Granted Authorities according to the entered Credential.
	 * 
	 * @param credential
	 * @return
	 */
	private List<GrantedAuthority> getGrantedAuthorities(final Credential credential) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		if (!CollectionUtil.isEmpty(credential.getRoles())) {
			credential.getRoles().forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role)));
		}
		return grantedAuthorities;
	}

	/**
	 * Retorna o token de acesso recuperados da instância
	 * {@link HttpServletRequest}.
	 * 
	 * @return
	 */
	private String getAccessToken(final HttpServletRequest request) {
		String accessToken = null;

		if (request != null) {
			accessToken = request.getHeader(AUTH_HEADER);

			if (!Util.isEmpty(accessToken)) {
				accessToken = accessToken.replaceAll(BEARER_PREFIX, "").trim();
			}
		}
		return accessToken;
	}

	/**
	 * Verifica se o token de acesso da request é 'Bearer'.
	 * 
	 * @param request
	 * @return
	 */
	private boolean isTokenBearer(final HttpServletRequest request) {
		boolean valid = Boolean.FALSE;

		if (request != null) {
			String accessToken = request.getHeader(AUTH_HEADER);
			valid = !Util.isEmpty(accessToken) && accessToken.trim().startsWith(BEARER_PREFIX);
		}
		return valid;
	}

}
