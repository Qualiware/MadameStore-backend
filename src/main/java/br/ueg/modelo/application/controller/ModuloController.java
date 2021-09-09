/*
 * ModuloController.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.modelo.application.controller;

import br.ueg.modelo.application.dto.ModuloDTO;
import br.ueg.modelo.application.mapper.ModuloMapper;
import br.ueg.modelo.application.model.Modulo;
import br.ueg.modelo.application.service.ModuloService;
import br.ueg.modelo.comum.exception.MessageResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de controle referente a entidade {@link Modulo}.
 * 
 * @author UEG
 */
@RestController
@Api(tags = "Módulo API")
@RequestMapping("${app.api.base}/modulos")
public class ModuloController extends AbstractController {

	@Autowired
	private ModuloService moduloService;

	@Autowired
	private ModuloMapper moduloMapper;

	/**
	 * Retorna uma lista de {@link ModuloDTO} ativos conforme o 'id' do Sistema informado.
	 *
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@ApiOperation(value = "Retorna uma lista de Módulos ativos conforme.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Success", response = ModuloDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) 
	})
	@GetMapping(path = "/modulo/ativos", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getAtivos() {
		List<Modulo> modulos = moduloService.getAtivos();
		List<ModuloDTO> modulosDTO = new ArrayList<>();
		for(Modulo modulo: modulos){
			modulosDTO.add(moduloMapper.toDTO(modulo));
		}
		return ResponseEntity.ok(modulosDTO);
	}
}
