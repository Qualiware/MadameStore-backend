/*
 * SwaggerConfig.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.configuration;

import org.springframework.context.annotation.Configuration;

import br.ueg.madamestore.api.config.ApiSwaggerConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Classe de configuração referente a geração de documentação automatida da API
 * REST.
 * 
 * @author UEG
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends ApiSwaggerConfig {

}
