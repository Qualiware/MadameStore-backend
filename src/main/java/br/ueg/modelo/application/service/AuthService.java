/*
 * AuthService.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.application.service;

import br.ueg.modelo.application.dto.AuthDTO;
import br.ueg.modelo.application.dto.CredencialDTO;
import br.ueg.modelo.application.dto.UsuarioSenhaDTO;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.model.Usuario;
import br.ueg.modelo.comum.exception.BusinessException;
import br.ueg.modelo.comum.util.Util;
import br.ueg.modelo.application.configuration.Constante;
import br.ueg.modelo.application.exception.SistemaMessageCode;
import br.ueg.modelo.application.security.CredentialImpl;
import br.ueg.modelo.application.security.KeyToken;
import br.ueg.modelo.application.security.TokenBuilder;
import br.ueg.modelo.application.security.TokenBuilder.JWTToken;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * Class de serviço responsável por prover as logicas de negócio referente a
 * Autenticação/Autorização.
 * 
 * @author UEG
 */
@Component
public class AuthService {

	@Autowired
	private KeyToken keyToken;

	@Autowired
	private GrupoService grupoService;


	@Autowired
	private UsuarioService usuarioService;


	@Value("${app.api.security.jwt.token-expire-in}")
	private Long tokenExpireIn;

	@Value("${app.api.security.jwt.token-refresh-in}")
	private Long tokenRefreshExpireIn;

	/**
	 * Autentica o Usuário concede um token de acesso temporário.
	 * 
	 * @param authDTO -
	 * @return -
	 */
	public CredencialDTO login(final AuthDTO authDTO)  {
		return loginAccess(authDTO);
	}

	public static Boolean loginByPassword(Usuario usuario, AuthDTO authDTO){
		if(!authDTO.getLogin().equals(usuario.getLogin())){
			return false;
		}
		// Tratamento de senha sem enconde (senha com menos de 21 caracteres
		if(usuario.getSenha().length()<=20) {
			if (authDTO.getSenha().equals(usuario.getSenha())) {
				return true;
			}
		}else{
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			if(bCryptPasswordEncoder.matches(authDTO.getSenha(),usuario.getSenha())){
				return true;
			}
		}
		return false;
	}

	/**
	 * Autentica o Usuário informado através do 'login' e 'senha' e concede um token
	 * de acesso temporário.
	 * 
	 * @param authDTO -
	 * @return -
	 */
	public CredencialDTO loginAccess(final AuthDTO authDTO)  {
		CredencialDTO credencialDTO = null;

		try {
			validarCamposObrigatoriosLoginAcesso(authDTO);

			Usuario usuario = usuarioService.getByLogin(authDTO.getLogin());
			validarUsuarioLogin(usuario);

			if (!loginByPassword(usuario, authDTO)) {
				usuario = salvaQuantidadeTentativaAcesso(usuario);
				throw new BusinessException(SistemaMessageCode.ERRO_USUARIO_SENHA_NAO_CONFEREM);
			}

			validarUltimoAcesso( usuario);

			credencialDTO = createCredencialDTO(usuario);

			TokenBuilder builder = new TokenBuilder(keyToken);
			builder.addNome(usuario.getNome());
			builder.addLogin(usuario.getLogin());
			builder.addParam(Constante.PARAM_EMAIL, usuario.getEmail());
			builder.addParam(Constante.PARAM_ID_USUARIO, usuario.getId());
			builder.addParam(Constante.PARAM_EXPIRES_IN, tokenExpireIn);
			builder.addParam(Constante.PARAM_REFRESH_EXPIRES_IN, tokenRefreshExpireIn);

			List<String> roles = null;

			roles = grupoService.getRolesByUsuario(usuario.getId());

			JWTToken accessToken = builder.buildAccess(tokenExpireIn);
			credencialDTO.setExpiresIn(accessToken.getExpiresIn());
			credencialDTO.setAccessToken(accessToken.getToken());

			JWTToken refreshToken = builder.buildRefresh(tokenRefreshExpireIn);
			credencialDTO.setRefreshExpiresIn(refreshToken.getExpiresIn());
			credencialDTO.setRefreshToken(refreshToken.getToken());
			credencialDTO.setRoles(roles);


			registerCredentialInSecurityContext(credencialDTO);
			ZerarQuantidadeTentativaAcesso(usuario);
			registrarQuantidadeAcesso(usuario);

			usuarioService.salvarUltimoAcesso(usuario);
		} catch (BadRequestException e) {
				throw e;
		}
		return credencialDTO;
	}

	/**
	 * cria o CredencialDTO utilizando o objeto usuário informado.
	 * @param usuario
	 * @return
	 */
	private CredencialDTO createCredencialDTO(Usuario usuario) {
		CredencialDTO credencialDTO;
		credencialDTO = new CredencialDTO();
		credencialDTO.setLogin(usuario.getLogin());
		credencialDTO.setEmail(usuario.getEmail());
		credencialDTO.setNome(usuario.getNome());
		credencialDTO.setId(usuario.getId());
		return credencialDTO;
	}

	/**
	 * Salva a quantidade de tentativas de acessos realizadas antes de ter sucesso.
	 * @param usuario
	 * @return
	 */
	private Usuario salvaQuantidadeTentativaAcesso(Usuario usuario){
		int qtdeTentativaAcesso = usuario.getQuantidadeTentativaAcesso() == null ? 0
				: Integer.parseInt(usuario.getQuantidadeTentativaAcesso());
		int count = qtdeTentativaAcesso + 1;
		usuario.setQuantidadeTentativaAcesso(Integer.toString(count));
		return usuarioService.salvar(usuario);
	}

	/**
	 * Zera a quantidade de tentativas de acesos realizadas pelo usuário informado.
	 * @param usuario
	 */
	private void ZerarQuantidadeTentativaAcesso(Usuario usuario){
		int qtdeTentativaAcesso = usuario.getQuantidadeTentativaAcesso() == null ? 0
				: Integer.parseInt(usuario.getQuantidadeTentativaAcesso());

		if(qtdeTentativaAcesso > 0) {
			usuario.setQuantidadeTentativaAcesso(null);
			usuarioService.salvar(usuario);
		}
	}

	/**
	 * registra a quantidade de acessos realizadas pelo usuário informado.
	 * @param usuario
	 */
	private void registrarQuantidadeAcesso(Usuario usuario){
		BigDecimal qtdeAcesso = usuario.getQuantidadeAcesso() == null ? BigDecimal.valueOf(0)
				: usuario.getQuantidadeAcesso();
		usuario.setQuantidadeAcesso(qtdeAcesso.add(BigDecimal.valueOf(1)));
		usuarioService.salvar(usuario);
	}

	/**
	 * Registra a credencial que acabou de fazer login no Contexto de segurança
	 * Motivação: criado para poder registrar auditoria das alterações realizadas na entidade
	 * de usuários durante o login.
	 * @param credencialDTO -
	 */
	private void registerCredentialInSecurityContext(CredencialDTO credencialDTO) {
		//Cria instancia da autenticação para ter informações para a auditoria
		CredentialImpl credential = CredentialImpl.newInstance(credencialDTO, credencialDTO.getAccessToken());
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(credential.getUsername(), credential);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}


	/**
	 * Gera um novo token de acesso atráves do refresh token informado.
	 * 
	 * @param refreshToken -
	 * @param mnemonico -
	 * @return -
	 */
	public CredencialDTO refresh(final String refreshToken, final String mnemonico) {
		AuthClaimResolve resolve = getClaimResolve(refreshToken);
		TokenBuilder builder = new TokenBuilder(keyToken);

		if (!resolve.isTokenTypeRefresh()) {
			throw new BusinessException(SistemaMessageCode.ERRO_TOKEN_INVALIDO);
		}

		List<String> roles = null;
		CredencialDTO credencialDTO = new CredencialDTO();


		roles = grupoService.getRolesByUsuario(resolve.getIdUsuario());


		credencialDTO.setNome(resolve.getNome());
		credencialDTO.setEmail(resolve.getEmail());
		credencialDTO.setLogin(resolve.getLogin());
		credencialDTO.setId(resolve.getIdUsuario());

		if (resolve.getIdUsuario() != null) {
			builder.addNome(resolve.getNome());
			builder.addLogin(resolve.getLogin());
			builder.addParam(Constante.PARAM_EMAIL, resolve.getEmail());
			builder.addParam(Constante.PARAM_ID_USUARIO, resolve.getIdUsuario());
		}

		Long expiresIn = resolve.getExpiresIn();
		builder.addParam(Constante.PARAM_EXPIRES_IN, expiresIn);

		Long refreshExpiresIn = resolve.getRefreshExpiresIn();
		builder.addParam(Constante.PARAM_REFRESH_EXPIRES_IN, refreshExpiresIn);

		JWTToken accessToken = builder.buildAccess(expiresIn);
		credencialDTO.setExpiresIn(accessToken.getExpiresIn());
		credencialDTO.setAccessToken(accessToken.getToken());

		JWTToken newRefreshToken = builder.buildRefresh(refreshExpiresIn);
		credencialDTO.setRefreshExpiresIn(newRefreshToken.getExpiresIn());
		credencialDTO.setRefreshToken(newRefreshToken.getToken());
		credencialDTO.setRoles(roles);
		return credencialDTO;
	}

	/**
	 * Retorna as informações do {@link CredencialDTO} conforme o 'token' informado.
	 * 
	 * @param token -
	 * @return -
	 */
	public CredencialDTO getInfoByToken(final String token) {
		AuthClaimResolve resolve = getClaimResolve(token);

		if (!resolve.isTokenTypeAccess()) {
			throw new BusinessException(SistemaMessageCode.ERRO_TOKEN_INVALIDO);
		}

		List<String> roles = null;
		CredencialDTO credencialDTO = new CredencialDTO();

		roles = grupoService.getRolesByUsuario(resolve.getIdUsuario());

		credencialDTO.setId(resolve.getIdUsuario());
		credencialDTO.setLogin(resolve.getLogin());
		credencialDTO.setEmail(resolve.getEmail());
		credencialDTO.setNome(resolve.getNome());
		credencialDTO.setRoles(roles);
		return credencialDTO;
	}

	/**
	 * Realiza a inclusão ou alteração de senha do {@link Usuario}.
	 * 
	 * @param usuarioSenhaDTO -
	 * @param token -
	 * @return -
	 */
	public CredencialDTO redefinirSenha(final UsuarioSenhaDTO usuarioSenhaDTO, final String token) {
		AuthClaimResolve resolve = getClaimResolve(token);

		usuarioSenhaDTO.setIdUsuario(resolve.getIdUsuario());
		usuarioSenhaDTO.setTipo(resolve.getTipoRedefinicaoSenha());
		Usuario usuario = usuarioService.redefinirSenha(usuarioSenhaDTO);

		AuthDTO authDTO = new AuthDTO();
		authDTO.setLogin(usuario.getLogin());
		authDTO.setSenha(usuarioSenhaDTO.getNovaSenha());
		return loginAccess(authDTO);
	}

	/**
	 * Valida o token de alteração de senha.
	 * 
	 * @param token -
	 */
	public boolean getInfoByTokenValidacao(final String token) {
		AuthClaimResolve resolve = getClaimResolve(token);

		UsuarioSenhaDTO usuarioSenhaTO = new UsuarioSenhaDTO();
		usuarioSenhaTO.setTipo(resolve.getTipoRedefinicaoSenha());

		Long idUsuario = resolve.getIdUsuario();
		Usuario usuario = usuarioService.getById(idUsuario);
		return usuarioSenhaTO.isRecuperacao() || (usuarioSenhaTO.isAtivacao() && !usuario.isStatusAtivo());
	}


	/**
	 * Verifica se os campos de preechimento obrigatório foram informados.
	 * 
	 * @param authDTO -
	 */
	private void validarCamposObrigatoriosLoginAcesso(final AuthDTO authDTO) {
		if (Util.isEmpty(authDTO.getLogin()) || Util.isEmpty(authDTO.getSenha())) {
			throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
		}
	}

	/**
	 * Verifica se o último acesso tiver ocorrido a mais de 30 dias.
	 * 
	 * @param usuario -
	 */
	private void validarUltimoAcesso(final Usuario usuario)  {
		if (usuario.getUltimoAcesso() != null) {
			Long days = ChronoUnit.DAYS.between(usuario.getUltimoAcesso(), LocalDateTime.now());

			if (days > Constante.NUMERO_MAXIMO_DIAS_SEM_ACESSO) {
				usuario.setAcessoExpirado(StatusSimNao.SIM);
				usuarioService.salvar(usuario);
				throw new BusinessException(SistemaMessageCode.ERRO_USUARIO_BLOQUEADO);
			}
		}
	}

	/**
	 * Verifica se o {@link Usuario} informado é valido no momento do login.
	 * 
	 * @param usuario -
	 */
	private void validarUsuarioLogin(Usuario usuario)  {
		if (usuario == null) {
			throw new BusinessException(SistemaMessageCode.ERRO_USUARIO_NAO_ENCONTRADO);
		}

		registerCredentialInSecurityContext(createCredencialDTO(usuario));

		if (!usuario.isStatusAtivo()) {
			throw new BusinessException(SistemaMessageCode.ERRO_USUARIO_INATIVO);
		}
	}

	/**
	 * Retorna a instância de {@link AuthClaimResolve}.
	 * 
	 * @param token -
	 * @return -
	 */
	private AuthClaimResolve getClaimResolve(final String token) {
		String value = getAccessToken(token);
		TokenBuilder builder = new TokenBuilder(keyToken);
		Map<String, Claim> claims = builder.getClaims(value);

		if (claims == null) {
			throw new BusinessException(SistemaMessageCode.ERRO_TOKEN_INVALIDO);
		}
		return AuthClaimResolve.newInstance(claims);
	}

	/**
	 * Retorna o token de acesso recuperados da instância
	 * {@link HttpServletRequest}.
	 * 
	 * @return -
	 */
	private String getAccessToken(final String value) {
		String accessToken = null;

		if (!Util.isEmpty(value)) {
			accessToken = value.replaceAll(Constante.HEADER_AUTHORIZATION_BEARER, "").trim();
		}
		return accessToken;
	}
}
