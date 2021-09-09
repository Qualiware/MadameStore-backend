/*
 * ApiSwaggerConfig.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de configuração referente a geração de documentação automatida da API
 * REST.
 * 
 * @author UEG
 */
public class ApiSwaggerConfig extends WebMvcConfigurationSupport {

	public static final String SWAGGER_LICENSE_URL = "http://www.apache.org/licenses/LICENSE-2.0";

	public static final String SWAGGER_LICENSE = "Apache License 2.0";

	@Value("${app.api.swagger.title}")
	private String title;

	@Value("${app.api.base}")
	private String path;

	@Value("${app.api.version}")
	private String version;

	@Value("${app.api.swagger.base-package}")
	private String basePackage;

	/**
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	/**
	 * Retorna a lista de instâncias de {@link ResponseMessage}.
	 * 
	 * @return
	 */
	private List<ResponseMessage> getApiResponses() {
		List<ResponseMessage> responses = new ArrayList<ResponseMessage>();

		// Status 401
		responses.add(new ResponseMessageBuilder().code(HttpStatus.UNAUTHORIZED.value())
				.message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).build());

		// Status 403
		responses.add(new ResponseMessageBuilder().code(HttpStatus.FORBIDDEN.value())
				.message(HttpStatus.FORBIDDEN.getReasonPhrase()).build());

		// Status 500
		responses.add(new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).build());

		return responses;
	}

}
