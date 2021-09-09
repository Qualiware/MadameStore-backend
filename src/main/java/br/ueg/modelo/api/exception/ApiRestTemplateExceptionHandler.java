/*
 * RestTemplateExceptionHandler.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.api.exception;

import br.ueg.modelo.comum.exception.BusinessException;
import br.ueg.modelo.comum.exception.MessageResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;

/**
 * Classe handler responsável por interceptar e tratar as exceções de forma
 * amigavel para o client.
 * 
 * @author UEG
 */
public class ApiRestTemplateExceptionHandler implements ResponseErrorHandler {

	/**
	 * @see org.springframework.web.client.ResponseErrorHandler#hasError(org.springframework.http.client.ClientHttpResponse)
	 */
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return !HttpStatus.OK.equals(response.getStatusCode());
	}

	/**
	 * @see org.springframework.web.client.ResponseErrorHandler#handleError(org.springframework.http.client.ClientHttpResponse)
	 */
	@Override
	public void handleError(final ClientHttpResponse clientResponse) throws IOException {
		try (InputStream input = clientResponse.getBody()) {
			final ObjectMapper mapper = createObjectMapper();
			MessageResponse response = mapper.readValue(input, new TypeReference<MessageResponse>() {});
			throw new BusinessException(response);
		}
	}

	/**
	 * Retorna a instância de {@link ObjectMapper}.
	 * 
	 * @return -
	 */
	private static ObjectMapper createObjectMapper() {
		return new ObjectMapper().findAndRegisterModules().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

}
