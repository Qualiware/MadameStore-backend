/*
 * Constante.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.configuration;

/**
 * Classe responsável por manter as constantes da aplicação.
 * 
 * @author UEG
 */
public final class Constante {

	public static final String MNEMONICO_SISTEMA = "MODELOAPI";

	/* Entidades e informações de entidades do sistema */
	public static final String ENTIDADE_GRUPO = "1";
	public static final String ENTIDADE_USUARIO = "Usuario";
	public static final String ENTIDADE_TODOS = "Todos";

	/** JWT - Security */
	public static final String PARAM_TYPE = "type";
	public static final String PARAM_NAME = "nome";
	public static final String PARAM_EMAIL = "email";
	public static final String PARAM_ROLES = "roles";
	public static final String PARAM_LINK = "link";
	public static final String PARAM_DISABLED = "disabled";
	public static final String PARAM_ID_USUARIO = "idUsuario";
	public static final String PARAM_EXPIRES_IN = "expiresIn";
	public static final String PARAM_REFRESH_EXPIRES_IN = "refreshExpiresIn";
	public static final String PARAM_TIPO_REDEFINICAO_SENHA = "tipoRedefinicaoSenha";

	public static final Long PARAM_TIME_TOKEN_VALIDATION = 86400L;

	/** Authorization */
	public static final String HEADER_AUTHORIZATION = "Authorization";
	public static final String HEADER_AUTHORIZATION_BEARER = "Bearer ";
	public static final Long NUMERO_MAXIMO_DIAS_SEM_ACESSO = 90L;

	public static final String CRIACAO_USUARIO_TEMPLATE = "criacaoUsuario.html";
	public static final String ESQUECI_SENHA_TEMPLATE = "esqueciSenha.html";
	public static final String CRIACAO_USUARIO_ASSUNTO = "Criação de usuário para acesso ao Portal – UEG";
	public static final String ESQUECI_SENHA_ASSUNTO = "Redefinição de senha de acesso ao MadameStore";
	public static final String DATABASE_OWNER = "public";


	public static final String TAMANHO_MINIMO_PESQUISA_NOME = "4";

	/** Tipo de operação */
	public static final String TIPO_OPERACAO_INCLUSAO = "Inclusão";
	public static final String TIPO_OPERACAO_ALTERACAO = "Alteração";
	public static final String TIPO_OPERACAO_EXCLUSAO = "Exclusão";
	public static final String TIPO_OPERACAO_CONSULTA = "Consulta";

	/**
	 * Construtor privado para garantir o singleton.
	 */
	private Constante() {
	}
}
