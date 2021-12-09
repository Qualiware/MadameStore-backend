/*
 * SistemaMessageCode.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.exception;

import br.ueg.madamestore.comum.exception.MessageCode;

/**
 * Enum com os código de exceções/mensagens de negócio.
 * 
 * @author UEG S/A.
 */
public enum SistemaMessageCode implements MessageCode {

	ERRO_INESPERADO("ME001", 500),
	ERRO_TOKEN_INVALIDO("ME002", 403),

	ERRO_CAMPOS_OBRIGATORIOS("MSG-001", 400),
	MSG_SAIR_SISTEMA("MSG-002",400),
	ERRO_USUARIO_NAO_ENCONTRADO("MSG-003", 404),
	ERRO_USUARIO_SENHA_NAO_CONFEREM("MSG-004", 400),
	ERRO_USUARIO_INATIVO_AD("MSG-005", 400),
	MSG_REMOVER_ITEM("MSG-006", 400),
	MSG_OPERACAO_REALIZADA_SUCESSO("MSG-007", 400),
	ERRO_USUARIO_INATIVO("MSG-008",400),
	ERRO_USUARIO_SEM_PERMISSAO_ACESSO("MSG-009", 400),
	ERRO_CPF_EM_USO("MSG-013", 400),
	ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS("MSG-015", 404),
	ERRO_NENHUM_REGISTRO_ENCONTRADO("ME003", 404),
	ERRO_FILTRO_INFORMAR_OUTRO("MSG-016", 400),
	ERRO_TAMANHO_INSUFICIENTE_FILTRO_GRUPO("MSG-017", 400),
	ERRO_CPF_INVALIDO("MSG-018", 400),
	ERRO_TAMANHO_INSUFICIENTE_FILTRO_NOME("MSG-019", 400),
	ERRO_LOGIN_DUPLICADO("MSG-026", 400),
	ERRO_CPF_JA_INFORMADO("MSG-027", 400),


	ERRO_DOCUMENTO_JA_INFORMADO("MSG-029", 400),
	MSG_INATIVAR_ITEM("MSG-033", 400),

	MSG_ATIVAR_ITEM("MSG-034", 400),
	ERRO_GRUPO_DUPLICADO("MSG-035",400),
	ERRO_USUARIO_NAO_SELECIONADO("MSG-036", 400),
	ERRO_LOGIN_NAO_ENCONTRO_AD("MSG-038", 404),
	ERRO_GRUPO_OBRIGATORIO("MSG-039", 400),
	ERRO_DATA_FINAL_MENOR_INICIAL("MSG-041",400),
	ERRO_USUARIO_BLOQUEADO("MSG-042", 400),
	ERRO_SENHA_ANTERIOR_INCORRETA("MSG-004", 400),//TODO Definir a mensagem quando for implantar usuário com senha
	ERRO_SENHAS_DIFERENTES("MSG-004", 400),//TODO Definir a mensagem quando for implantar usuário com senha
	ERRO_SENHA_PADRAO_INVALIDO("MSG-004", 400),//TODO Definir a mensagem quando for implantar usuário com senha
	ERRO_USUARIO_NAO_PODE_ALTERAR_SENHA("MSG-004", 400),//TODO Definir a mensagem quando for implantar usuário com senha (não pode alterar po ser do ad)
	ERRO_SENHA_JA_FOI_UTILIZADA("MSG-004",400),//TODO Definir a mensagem quando for implantar usuário com senha
	ERRO_ORIGEM_CADASTRO("MSG-043", 400),
	ERRO_CLASSE_INCOMPATIVEL_CONSULTA_REGISTRO("ME004", 400),
	AUTH_USUARIO_AUTENTICADO("AUTH-001", 200),
    AUTH_SENHA_INVALIDA("AUTH-003", 200),
    AUTH_USUARIO_BLOQUEADO_AD("AUTH-004", 200),
    AUTH_USUARIO_BLOQUEADO_PORTAL("AUTH-005",200),
	ERRO_DATA_INICIAL_MAIOR_DATA_FINAL("MSG-044", 400),
	ERRO_DATA_FILTRO_FUTURA("MSG-020",400),

	ERRO_TAMANHO_INSUFICIENTE_FILTRO_TIPOAMIGO("MSG-021", 400),

	ERRO_TIPO_AMIGO_DUPLICADO("MSG-045",400),

	ERRO_AMIGO_DUPLICADO("MSG-046", 400),
	ERRO_QUANTIDADE_DE_PRODUTOS_INSUFICIENTE("MSG-050", 400),
	ERRO_PRODUTO_NAO_ENCONTRADO("MSG-051", 400),

	ERRO_QUANTIDADE("MSG-053",400),

	ERRO_CLIENTE_NAO_ENCONTRADO("MSG-052", 400)
            ;

	private final String code;

	private final Integer status;

	/**
	 * Construtor da classe.
	 * 
	 * @param code -
	 * @param status -
	 */
	private SistemaMessageCode(final String code, final Integer status) {
		this.code = code;
		this.status = status;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @see Enum#toString()
	 */
	@Override
	public String toString() {
		return code;
	}
}
