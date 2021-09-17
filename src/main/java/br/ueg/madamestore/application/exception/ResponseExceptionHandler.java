/*
 * ResponseExceptionHandler.java
 * Copyright (c) UEG.
 */
package br.ueg.madamestore.application.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.ueg.madamestore.api.exception.ApiRestResponseExceptionHandler;
import br.ueg.madamestore.comum.exception.MessageCode;

import javax.ws.rs.BadRequestException;

/**
 * Classe handler responsável por interceptar e tratar as exceções de forma
 * amigavel para o client.
 * 
 * @author Squadra Tecnologia
 */
@ControllerAdvice
public class ResponseExceptionHandler extends ApiRestResponseExceptionHandler {

	/**
	 * @see br.ueg.madamestore.api.exception.ApiRestResponseExceptionHandler#getCodeInternalServerError()
	 */
	@Override
	protected MessageCode getCodeInternalServerError() {
		return SistemaMessageCode.ERRO_INESPERADO;
	}

	/**
	 * Handler responsável por tratar exeções do tipo {@link BadRequestException}.
	 * 
	 * @param e
	 */
	@ExceptionHandler({ BadRequestException.class })
	public ResponseEntity<Object> badRequestExceptionHandler(final BadRequestException e) {
//		KeycloakResponseError response = KeycloakResponseError.newInstance(e);
//
//		if (response.getKeycloakError() != null) {
//			return handleBusinessException(new BusinessException(response.getKeycloakError().getCode()));
//		}
		return handleException(e);
	}

}
