/*
 * AuthController.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.modelo.application.controller;

import br.ueg.modelo.application.dto.AuthDTO;
import br.ueg.modelo.application.dto.CredencialDTO;
import br.ueg.modelo.application.dto.UsuarioDTO;
import br.ueg.modelo.application.dto.UsuarioSenhaDTO;
import br.ueg.modelo.application.model.Usuario;
import br.ueg.modelo.application.service.AuthService;
import br.ueg.modelo.application.service.UsuarioService;
import br.ueg.modelo.comum.exception.MessageResponse;
import br.ueg.modelo.comum.util.Util;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * Classe de controle de Autenticação de Usuário.
 * 
 * @author UEG
 */
@RestController
@Api(tags = "Auth API")
@RequestMapping("${app.api.base}/auth")
public class AuthController extends AbstractController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Autentica o Usuário informado através dos parâmetros informados.
	 * 
	 * @param authTO
	 * @return
	 */
	@ApiOperation(value = "Concede o token de acesso ao Usuário através do 'login' e 'senha'.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Success", response = CredencialDTO.class),
			@ApiResponse(code = 403, message = "Proibido", response = MessageResponse.class), 
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
	})
	@PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@ApiParam(value = "Informações de Autenticação", required = true) @Valid @RequestBody final AuthDTO authTO)  {
		CredencialDTO credencialTO = authService.login(authTO);
		return ResponseEntity.ok(credencialTO);
	}

	/**
	 * Concede um novo token de acesso conforme o token de refresh informado.
	 * 
	 * @param refreshToken
	 * @return
	 */
	@ApiOperation(value = "Concede um novo token de acesso conforme o token de refresh informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Success", response = UsuarioDTO.class),
			@ApiResponse(code = 403, message = "Proibido", response = MessageResponse.class), 
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
	})
	@GetMapping(path = "/refresh", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> refresh(
			@ApiParam(value = "Token de refresh", required = true) @RequestParam() final String refreshToken,
			@ApiParam(value = "Mnemonico do Sistema") @RequestParam(required = false) final String mnemonico) {
		CredencialDTO credencialTO = authService.refresh(refreshToken, mnemonico);
		return ResponseEntity.ok(credencialTO);
	}

	/**
	 * Retorna as informações do Usuário conforme o 'token' informado.
	 * 
	 * @param accessToken -
	 * @return -
	 */
	@ApiOperation(value = "Recupera as informações do Usuário conforme o token informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Success", response = UsuarioDTO.class),
			@ApiResponse(code = 403, message = "Proibido", response = MessageResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class) 
	})
	@GetMapping(path = "/info", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getInfoByToken(
			@ApiParam(value = "Token", required = true) @RequestHeader( name = "Authorization") final String accessToken) {
		CredencialDTO credencialTO = authService.getInfoByToken(accessToken);
		return ResponseEntity.ok(credencialTO);
	}

	/**
	 * Realiza a inclusão ou alteração de senha do {@link Usuario}
	 * 
	 * @param tokenParam
	 * @param tokenHeader
	 * @param usuarioSenhaTO
	 * @return
	 */
	@ApiOperation(value = "Inclusão ou alteração a senha do usuário.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Success", response = CredencialDTO.class),
		@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class) 
	})
	@PutMapping(path = "/senha", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> redefinirSenha(
			@ApiParam(value = "Request Token") @RequestParam(name = "requestToken", required = false) final String tokenParam,
			@ApiParam(value = "Request Token") @RequestHeader(name = "Request-Token", required = false) final String tokenHeader,
			@ApiParam(value = "Informações da Redefinição de Senha", required = true) @Valid @RequestBody UsuarioSenhaDTO usuarioSenhaTO) {
		ResponseEntity<?> response = null;
		final String token = Util.isEmpty(tokenParam) ? tokenHeader : tokenParam;

		if (Util.isEmpty(token)) {
			response = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		} else {
			CredencialDTO credencialTO = authService.redefinirSenha(usuarioSenhaTO, token);
			response = ResponseEntity.ok(credencialTO);
		}
		return response;
	}
	
	/**
	 * Realiza a solicitação de recuperar a senha do {@link Usuario}
	 * 
	 * @param cpf -
	 * @return -
	 */
	@ApiOperation(value = "Realiza a solicitação de recuperar a senha do usuário.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Success", response = UsuarioDTO.class),
		@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class) 
	})
	@GetMapping(path = "/senha/solicitacao/{cpf:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> recuperarSenha(
			@ApiParam(value = "CPF do Usuário", required = true) @PathVariable() final String cpf) {
		Usuario usuario = usuarioService.recuperarSenha(cpf);
		String email = Util.getEmailObfuscado(usuario.getEmail());
		return ResponseEntity.ok(new UsuarioSenhaDTO(email));
	}

	/**
	 * Valida o token de alteração de senha.
	 * 
	 * @param tokenParam -
	 * @param tokenHeader -
	 * @return
	 */
	@ApiOperation(value = "Valida o token de alteração de senha.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Success", response = Boolean.class),
			@ApiResponse(code = 403, message = "Proibido", response = MessageResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class) 
	})
	@GetMapping(path = "/senha/solicitacao/info", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getInfoByTokenValidacao(
			@ApiParam(value = "Request Token") @RequestParam(name = "requestToken", required = false) final String tokenParam,
			@ApiParam(value = "Request Token") @RequestHeader(name = "Request-Token", required = false) final String tokenHeader) {
		final String token = Util.isEmpty(tokenParam) ? tokenHeader : tokenParam;
		boolean valido = authService.getInfoByTokenValidacao(token);
		return ResponseEntity.ok(valido);
	}

	/**
	 * Verifica se o Usuarío possui permissão de acesso para o Sistema conforme o
	 * mnemonico informado.
	 *
	 * @param mnemonico
	 */
	@PreAuthorize("isAuthenticated()")
	@ApiOperation(value = " Verifica se o Usuarío possui permissão de acesso para o Sistema conforme mnemonico informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) 
	})
	@GetMapping(path = "/permissao/validacao", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> validarPermissaoUsuario(
			@ApiParam(value = "Mnemonico do Sistema", required = true) @RequestParam() final String mnemonico) {
		Long idUsuario = getIdUsuarioLogado();
		//TODO Reescrever após concluir mapeamento das entidades
		//permissaoService.validarPermissaoUsuario(idUsuario, mnemonico);
		return ResponseEntity.ok().build();
	}
}
