/*
 * EmailEngineService.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.application.service;

import br.ueg.modelo.application.dto.UsuarioSenhaDTO;
import br.ueg.modelo.application.model.Usuario;
import br.ueg.modelo.comum.email.Email;
import br.ueg.modelo.comum.email.EmailException;
import br.ueg.modelo.comum.exception.BusinessException;
import br.ueg.modelo.application.configuration.Constante;
import br.ueg.modelo.application.security.KeyToken;
import br.ueg.modelo.application.security.TokenBuilder;
import br.ueg.modelo.application.security.TokenBuilder.JWTToken;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe de serviço de envio de e-mails.
 *
 * @author UEG
 */
@Service
public class EmailEngineService {

	@Value("${app.api.auth.url-redefinir-senha}")
	private String urlRedefinirSenha;

	@Autowired
	private Configuration freemarkerConfig;

	@Autowired
	private KeyToken keyToken;

	/**
	 * Envia o e-mail de ativação do {@link Usuario}
	 * 
	 * @param usuario -
	 */
	public void enviarEmailAtivacaoUsuario(final Usuario usuario) {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put(Constante.PARAM_NAME, usuario.getNome());

			String url = getURLValidacao(usuario, UsuarioSenhaDTO.TipoRedefinicaoSenha.ativacao);
			params.put(Constante.PARAM_LINK, url);
			Email mail = new Email();
			mail.setSubject(Constante.CRIACAO_USUARIO_ASSUNTO);

			Template template = freemarkerConfig.getTemplate(Constante.CRIACAO_USUARIO_TEMPLATE);
			String body = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
			mail.setBody(body);

			mail.addAddressTO(usuario.getEmail()).send();
		} catch (EmailException | IOException | TemplateException e) {
			throw new BusinessException(e);
		}
	}

	/**
	 * Envia o e-mail de esqueci a senha do {@link Usuario}
	 * 
	 * @param usuario -
	 */
	public void enviarEmailEsqueciSenha(final Usuario usuario) {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put(Constante.PARAM_NAME, usuario.getNome());

			String url = getURLValidacao(usuario, UsuarioSenhaDTO.TipoRedefinicaoSenha.recuperacao);
			params.put(Constante.PARAM_LINK, url);

			Email mail = new Email();
			mail.setSubject(Constante.ESQUECI_SENHA_ASSUNTO);

			Template template = freemarkerConfig.getTemplate(Constante.ESQUECI_SENHA_TEMPLATE);
			String body = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
			mail.setBody(body);

			mail.addAddressTO(usuario.getEmail()).send();
		} catch (EmailException | IOException | TemplateException e) {
			throw new BusinessException(e);
		}
	}

	/**
	 * Retorna a URL de ativação do {@link Usuario}
	 * 
	 * @param usuario -
	 * @param tipo -
	 * @return -
	 */
	private String getURLValidacao(final Usuario usuario, final UsuarioSenhaDTO.TipoRedefinicaoSenha tipo) {
		TokenBuilder builder = new TokenBuilder(keyToken);
		builder.addParam(Constante.PARAM_ID_USUARIO, usuario.getId());
		builder.addParam(Constante.PARAM_TIPO_REDEFINICAO_SENHA, tipo.toString());

		JWTToken token = builder.buildValidation(Constante.PARAM_TIME_TOKEN_VALIDATION);
		return urlRedefinirSenha + token.getToken();
	}
}