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
import br.ueg.madamestore.application.dto.FiltroMensagemDTO;
import br.ueg.madamestore.application.dto.FiltroVendaDTO;
import br.ueg.madamestore.application.dto.MensagemDTO;
import br.ueg.madamestore.application.dto.VendaDTO;
import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.enums.TipoRetirada;
import br.ueg.madamestore.application.mapper.EsperaMapper;
import br.ueg.madamestore.application.mapper.MensagemMapper;
import br.ueg.madamestore.application.mapper.VendaMapper;
import br.ueg.madamestore.application.mapper.VendidoMapper;
import br.ueg.madamestore.application.model.Mensagem;
import br.ueg.madamestore.application.model.Venda;
import br.ueg.madamestore.application.service.MensagemService;
import br.ueg.madamestore.application.service.VendaService;
import br.ueg.madamestore.comum.exception.MessageResponse;
import io.swagger.annotations.*;
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
 * Classe de controle referente a entidade {@link Mensagem}.
 * 
 * @author UEG
 */
@Api(tags = "Mensagem API")
@RestController
@RequestMapping("${app.api.base}/mensagem")
public class MensagemController extends AbstractController {

	@Autowired
	private MensagemService mensagemService;

	@Autowired
	private MensagemMapper mensagemMapper;





	/**
	 * Salva uma instância de {@link Mensagem} na base de dados.
	 * 
	 * @param mensagemDTO
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_PRODUTO_INCLUIR')")
	@ApiOperation(value = "Inclui um novo Usuário na base de dados.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) 
	})
	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> incluir(@ApiParam(value = "Informações da Venda", required = true) @Valid @RequestBody MensagemDTO mensagemDTO) {

		Mensagem mensagem= mensagemMapper.toEntity(mensagemDTO);
		if(mensagemDTO.getDescricaoTipo()==TipoRetirada.INCLUSAO.getDescricao()){
			mensagemService.add(mensagem);
		}
		if(mensagemDTO.getDescricaoTipo()==TipoRetirada.PERDA.getDescricao()){
			mensagemService.retira(mensagem);
		}
		mensagem = mensagemService.salvar(mensagem);
		mensagemDTO = mensagemMapper.toDTO(mensagem);
		return ResponseEntity.ok(mensagemDTO);
	}

    /**
	 * Altera a instância de {@link Mensagem} na base de dados.
	 * 
	 * @param id
	 * @param mensagemDTO
	 * @return
	 */
    @PreAuthorize("hasRole('ROLE_PRODUTO_ALTERAR')")
    @ApiOperation(value = "Altera as informações de venda na base de dados.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(@ApiParam(value = "Código do Usuário", required = true) @PathVariable final BigDecimal id, @ApiParam(value = "Informações de venda", required = true) @Valid @RequestBody MensagemDTO mensagemDTO) {
        Validation.max("id", id, 99999999L);
		Mensagem mensagem = mensagemMapper.toEntity(mensagemDTO);
		//mensagemService.configurarVendaProduto(mensagem);
		mensagem.setId(id.longValue());
		//vendaService.retiraVendaAlterarQuantidade(venda);
		//vendaService.diminuirQuantidadeVendida(venda);
		mensagemService.retiraQuantidade(mensagem);
		mensagemService.salvar(mensagem);
		return ResponseEntity.ok(mensagemDTO);
    }

	/**
	 * Retorna a instância de {@link VendaDTO} conforme o 'id'
	 * informado.
	 *
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_PRODUTO_VISUALIZAR')")
	@ApiOperation(value = "Recupera o venda pela id.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
	})
	@GetMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getMensagemById(@ApiParam(value = "Id do Venda") @PathVariable final BigDecimal id) {
		Validation.max("id", id, 99999999L);
		Mensagem mensagem = mensagemService.getByIdFetch(id.longValue());
		MensagemDTO mensagemDTO = new MensagemDTO();

		if(mensagem != null)
			mensagemDTO = mensagemMapper.toDTO(mensagem);

		return ResponseEntity.ok(mensagemDTO);
	}


	/**
	 * Retorna a lista de {@link VendaDTO} de acordo com os filtros
	 * informados.
	 *
	 * @param filtroDTO
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_PRODUTO_PESQUISAR')")
	@ApiOperation(value = "Recupera as vendas pelo Filtro Informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
	})
	@GetMapping(path = "/filtro-ativo", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getVendaAtivosByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @Valid @ModelAttribute("filtroDTO") FiltroMensagemDTO filtroDTO) {

		List<MensagemDTO> mensagensDTO = new ArrayList<>();
		List<Mensagem> mensagens = mensagemService.getMensagemByFiltro(filtroDTO);
		if(mensagens != null){
			for (Mensagem mensagem: mensagens) {
				mensagensDTO.add(mensagemMapper.toDTO(mensagem));
			}
		}

		return ResponseEntity.ok(mensagensDTO);
	}

	/**
	 * Retorna a lista de {@link VendaDTO} de acordo com os filtros
	 * informados.
	 *
	 * @param filtroDTO
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_PRODUTO_PESQUISAR')")
	@ApiOperation(value = "Recupera os vendas pelo Filtro Informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
	})
	@GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getMensagemByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @Valid @ModelAttribute("filtroDTO") final FiltroMensagemDTO filtroDTO) {
		List<Mensagem> mensagens = mensagemService.getMensagemByFiltro(filtroDTO);
		List<MensagemDTO> mensagensDTO = new ArrayList<>();
		if(mensagens.size() > 0){
			for (Mensagem g:

					mensagens) {
				MensagemDTO mensagemDTO = mensagemMapper.toDTO(g);
				mensagensDTO.add(mensagemDTO);
			}
		}

		return ResponseEntity.ok(mensagensDTO);
	}

}
