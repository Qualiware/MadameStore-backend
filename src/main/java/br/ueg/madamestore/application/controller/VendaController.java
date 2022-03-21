/*
 * UsuarioController.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.controller;

import br.ueg.madamestore.api.util.Validation;
import br.ueg.madamestore.application.dto.*;
import br.ueg.madamestore.application.enums.StatusAtivoInativo;
import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.mapper.EsperaMapper;
import br.ueg.madamestore.application.mapper.UsuarioMapper;
import br.ueg.madamestore.application.mapper.VendaMapper;
import br.ueg.madamestore.application.mapper.VendidoMapper;
import br.ueg.madamestore.application.model.Amigo;
import br.ueg.madamestore.application.model.Produto;
import br.ueg.madamestore.application.model.Usuario;
import br.ueg.madamestore.application.model.Venda;
import br.ueg.madamestore.application.service.UsuarioService;
import br.ueg.madamestore.application.service.VendaService;
import br.ueg.madamestore.comum.exception.MessageResponse;
import io.swagger.annotations.*;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de controle referente a entidade {@link Venda}.
 * 
 * @author UEG
 */
@Api(tags = "Venda API")
@RestController
@RequestMapping("${app.api.base}/venda")
public class VendaController extends AbstractController {

	@Autowired
	private VendaService vendaService;

	@Autowired
	private VendaMapper vendaMapper;

	@Autowired
	private EsperaMapper esperaMapper;

	@Autowired
	private VendidoMapper vendidoMapper;


	/**
	 * Salva uma instância de {@link Venda} na base de dados.
	 * 
	 * @param vendaDTO
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_VENDA_INCLUIR')")
	@ApiOperation(value = "Inclui um novo Usuário na base de dados.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) 
	})
	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> incluir(@ApiParam(value = "Informações da Venda", required = true) @Valid @RequestBody VendaDTO vendaDTO) {
		vendaDTO.setStatusVendido(false);
		vendaDTO.setStatusEspera(true);
		Venda venda=vendaMapper.toEntity(vendaDTO);



		//vendaService.configurarVendaProduto(venda);

		venda = vendaService.salvar(venda);
		vendaService.retiraQuantidade(venda);
		vendaDTO = vendaMapper.toDTO(venda);
		return ResponseEntity.ok(vendaDTO);
	}

    /**
	 * Altera a instância de {@link Venda} na base de dados.
	 * 
	 * @param id
	 * @param vendaDTO
	 * @return
	 */
    @PreAuthorize("hasRole('ROLE_VENDA_ALTERAR')")
    @ApiOperation(value = "Altera as informações de venda na base de dados.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(@ApiParam(value = "Código do Usuário", required = true) @PathVariable final BigDecimal id, @ApiParam(value = "Informações de venda", required = true) @Valid @RequestBody VendaDTO vendaDTO) {
        Validation.max("id", id, 99999999L);
		Venda venda = vendaMapper.toEntity(vendaDTO);
		vendaService.configurarVendaProduto(venda);
		venda.setId(id.longValue());
		//vendaService.retiraVendaAlterarQuantidade(venda);
		//vendaService.diminuirQuantidadeVendida(venda);
		vendaService.retiraQuantidade(venda);
		vendaService.salvar(venda);
		return ResponseEntity.ok(vendaDTO);
    }

	/**
	 * Retorna a instância de {@link VendaDTO} conforme o 'id'
	 * informado.
	 *
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_VENDA_VISUALIZAR')")
	@ApiOperation(value = "Recupera o venda pela id.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
	})
	@GetMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getVendaById(@ApiParam(value = "Id do Venda") @PathVariable final BigDecimal id) {
		Validation.max("id", id, 99999999L);
		Venda venda = vendaService.getByIdFetch(id.longValue());
		VendaDTO vendaTO = new VendaDTO();

		if(venda != null)
			vendaTO = vendaMapper.toDTO(venda);

		return ResponseEntity.ok(vendaTO);
	}

	/**
	 * Altera a instância de {@link Venda} na base de dados.
	 *
	 * @param id
	 * @param vendaDTO
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_VENDA_ALTERAR')")
	@ApiOperation(value = "Altera as informações de venda na base de dados.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
	})
	@PutMapping(path = "/alterar/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<?> alterarProduto(@ApiParam(value = "Código do Usuário", required = true) @PathVariable final BigDecimal id, @ApiParam(value = "Informações de venda", required = true) @Valid @RequestBody VendaDTO vendaDTO) {
		Validation.max("id", id, 99999999L);
		Venda venda = vendaMapper.toEntity(vendaDTO);
		vendaService.configurarVendaProduto(venda);
		venda.setId(id.longValue());
		vendaService.adicionaValoresProduto(venda);
		return ResponseEntity.ok(vendaDTO);
	}


	/**
	 * Retorna a lista de {@link VendaDTO} de acordo com os filtros
	 * informados.
	 * 
	 * @param filtroDTO
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_VENDA_PESQUISAR')")
	@ApiOperation(value = "Recupera as vendas pelo Filtro Informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) 
	})
	@GetMapping(path = "/filtro-ativo", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getVendaAtivosByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @Valid @ModelAttribute("filtroDTO") FiltroVendaDTO filtroDTO) {

		List<VendaDTO> vendasDTO = new ArrayList<>();
		List<Venda> vendas = vendaService.getVendaByFiltro(filtroDTO);
		if(vendas != null){
			for (Venda venda: vendas) {
				vendasDTO.add(vendaMapper.toDTO(venda));
			}
		}

		return ResponseEntity.ok(vendasDTO);
	}

	/**
	 * Retorna a lista de {@link VendaDTO} de acordo com os filtros
	 * informados.
	 * 
	 * @param filtroDTO
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_VENDA_PESQUISAR')")
	@ApiOperation(value = "Recupera os vendas pelo Filtro Informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
		@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) 
	})
	@GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getVendaByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @Valid @ModelAttribute("filtroDTO") final FiltroVendaDTO filtroDTO) {
		List<Venda> vendas = vendaService.getVendaByFiltro(filtroDTO);
		List<VendaDTO> vendasDTO = new ArrayList<>();
		if(vendas.size() > 0){
			for (Venda g:

					vendas) {
				VendaDTO vendaDTO = vendaMapper.toDTO(g);
				vendasDTO.add(vendaDTO);
			}
		}

		return ResponseEntity.ok(vendasDTO);
	}

	@ApiOperation(value = "Recupera os vendas pelo Filtro Informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
	})
	@GetMapping(path = "/all", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getAll() {
		List<Venda> vendas = vendaService.getVendas();
		List<VendaDTO> vendasDTO = new ArrayList<>();
		if(vendas.size() > 0){
			for (Venda g:

					vendas) {
				VendaDTO vendaDTO = vendaMapper.toDTO(g);
				vendasDTO.add(vendaDTO);
			}
		}

		return ResponseEntity.ok(vendasDTO);
	}



	/**
	 * Tornar Amigo do {@link Venda} pelo 'id' informado.
	 *
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_VENDA_INCLUIR')")
	@ApiOperation(value = "Tonar Venda pendente pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
	})
	@PutMapping(path = "/{id:[\\d]+}/tornar-vendaespera", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> tonarVendaEspera(@ApiParam(value = "Id da venda", required = true) @PathVariable final BigDecimal id) {
		Validation.max("id", id, 99999999L);
		Venda venda = vendaService.getById(id.longValue());
		venda.setStatusVendido(StatusVendido.NAO);
		venda.setStatusEspera(StatusEspera.SIM);
		vendaService.salvar(venda);
		return ResponseEntity.ok(vendaMapper.toDTO(venda));
	}



	/**
	 * Tornar Amigo do {@link Venda} pelo 'id' informado.
	 *
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_VENDA_INCLUIR')")
	@ApiOperation(value = "Tonar Venda pendente pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
	})
	@PutMapping(path = "/{id:[\\d]+}/deixar-vendaespera", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> deixarVendaEspera(@ApiParam(value = "Id da Venda", required = true) @PathVariable final BigDecimal id) {
		Validation.max("id", id, 99999999L);
		Venda venda = vendaService.getById(id.longValue());
		venda.setStatusEspera(StatusEspera.NAO);
		vendaService.salvar(venda);
		return ResponseEntity.ok(vendaMapper.toDTO(venda));
	}

	/**
	 * Tornar Amigo do {@link Venda} pelo 'id' informado.
	 *
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_VENDA_INCLUIR')")
	@ApiOperation(value = "Tonar Venda pendente pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
	})
	@PutMapping(path = "/{id:[\\d]+}/deixar-vendido", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> deixarVendido(@ApiParam(value = "Id da Venda", required = true) @PathVariable final BigDecimal id) {
		Validation.max("id", id, 99999999L);
		Venda venda = vendaService.getById(id.longValue());
		venda.setStatusVendido(StatusVendido.NAO);
		vendaService.salvar(venda);
		return ResponseEntity.ok(vendaMapper.toDTO(venda));
	}


	/**
	 * Tornar Amigo do {@link Venda} pelo 'id' informado.
	 *
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_VENDA_INCLUIR')")
	@ApiOperation(value = "Tonar Venda pendente pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
	})
	@PutMapping(path = "/{id:[\\d]+}/tornar-vendido", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> tornarVendido(@ApiParam(value = "Id da Venda", required = true) @PathVariable final BigDecimal id) {
		Validation.max("id", id, 99999999L);
		Venda venda = vendaService.getById(id.longValue());
		venda.setStatusEspera(StatusEspera.NAO);
		venda.setStatusVendido(StatusVendido.SIM);
		vendaService.salvar(venda);
		return ResponseEntity.ok(vendaMapper.toDTO(venda));
	}

	@PreAuthorize("hasRole('ROLE_VENDA_REMOVER')")
	@ApiOperation(value = "Remove uma venda pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
					@ApiResponse(code = 200, message = "Success", response = ProdutoDTO.class),
					@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
					@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
	})
	@DeleteMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> remover(@ApiParam(value = "Id da venda", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);

				Venda venda = vendaService.remover(id.longValue());
				
        return ResponseEntity.ok(vendaMapper.toDTO(venda));
    }

	@ApiOperation(value = "Tonar Venda pendente pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
	})
	@PutMapping(path = "/{id:[\\d]+}/finalizar", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> finalizarVenda(@ApiParam(value = "Id da Venda", required = true) @PathVariable final BigDecimal id) {
		Validation.max("id", id, 99999999L);
		Venda venda = vendaService.getById(id.longValue());
		venda.setStatusEspera(StatusEspera.NAO);
		venda.setStatusVendido(StatusVendido.SIM);
		vendaService.salvar(venda);
		return ResponseEntity.ok(vendaMapper.toDTO(venda));
	}

    @ApiOperation(value = "Inclui um novo Usuário na base de dados.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PostMapping(path = "/incluir", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> incluirVenda(@ApiParam(value = "Informações da Venda", required = true) @Valid @RequestBody VendaDTO vendaDTO) {
        vendaDTO.setStatusVendido(false);
        vendaDTO.setStatusEspera(true);
        Venda venda=vendaMapper.toEntity(vendaDTO);



        //vendaService.configurarVendaProduto(venda);

        venda = vendaService.salvar(venda);
        vendaService.retiraQuantidade(venda);
        vendaDTO = vendaMapper.toDTO(venda);
        return ResponseEntity.ok(vendaDTO);
    }

}
