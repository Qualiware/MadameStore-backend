package br.ueg.modelo.application.controller;

import br.ueg.modelo.api.util.Validation;
import br.ueg.modelo.application.dto.FiltroTipoAmigoDTO;
import br.ueg.modelo.application.dto.TipoAmigoDTO;
import br.ueg.modelo.application.mapper.TipoAmigoMapper;
import br.ueg.modelo.application.model.Grupo;
import br.ueg.modelo.application.model.TipoAmigo;
import br.ueg.modelo.application.service.TipoAmigoService;
import br.ueg.modelo.comum.exception.MessageResponse;
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

@Api(tags = "TipoAmigo API")
@RestController
@RequestMapping(path = "${app.api.base}/tipo-amigo")
public class TipoAmigoController extends AbstractController {

    @Autowired
    private TipoAmigoMapper tipoAmigoMapper;

    @Autowired
    private TipoAmigoService tipoAmigoService;

    @PreAuthorize("hasRole('ROLE_TIPOAMIGO_INCLUIR')")
    @PostMapping
    @ApiOperation(value = "Inclusão/alteração de tipo amigo.",
            notes = "Incluir/Alterar Tipo Amigo.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoAmigoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações de Tipo Amigo", required = true) @Valid @RequestBody TipoAmigoDTO tipoAmigoDTO) {
            TipoAmigo grupo = tipoAmigoMapper.toEntity(tipoAmigoDTO);
            return ResponseEntity.ok(tipoAmigoMapper.toDTO(tipoAmigoService.salvar(grupo)));
    }

    /**
     * Altera a instância de {@link TipoAmigoDTO} na base de dados.
     *
     * @param id
     * @param tipoAmigoDTO
     * @return
     */
    @PreAuthorize("hasRole('ROLE_TIPOAMIGO_ALTERAR')")
    @ApiOperation(value = "Altera as informações de Tipo Amigo.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoAmigoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(
            @ApiParam(value = "Código do Tipo Amigo", required = true) @PathVariable final BigDecimal id,
            @ApiParam(value = "Informações do Tipo Amigo", required = true) @Valid @RequestBody TipoAmigoDTO tipoAmigoDTO) {
        Validation.max("id", id, 99999999L);
        TipoAmigo tipoAmigo = tipoAmigoMapper.toEntity(tipoAmigoDTO);
        tipoAmigo.setId(id.longValue());
        TipoAmigo tipoAmigoSaved = tipoAmigoService.salvar(tipoAmigo);
        return ResponseEntity.ok(tipoAmigoMapper.toDTO(tipoAmigoSaved));
    }

    /**
     * Retorna a instância de {@link TipoAmigoDTO} pelo id informado.
     *
     * @param id
     * s@return
     */
    @PreAuthorize("hasRole('ROLE_TIPOAMIGO_PESQUISAR')")
    @ApiOperation(value = "Retorna as informações do TipoAmigo pelo id informado.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoAmigoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@ApiParam(value = "Código do Grupo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        TipoAmigo tipoAmigo = tipoAmigoService.getById(id.longValue());
        TipoAmigoDTO tipoAmigoDTO = tipoAmigoMapper.toDTO(tipoAmigo);

        return ResponseEntity.ok(tipoAmigoDTO);
    }

    /**
     * Retorna a buscar de {@link TipoAmigo} por {@link FiltroTipoAmigoDTO}
     *
     * @param filtroTipoAmigoDTO
     * @return
     */
    @PreAuthorize("hasRole('ROLE_TIPOAMIGO_PESQUISAR')")
    @ApiOperation(value = "Pesquisa de TipoAmigo.",
            notes = "Recupera as informações de TipoAmigo conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoAmigoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroTipoAmigoDTO filtroTipoAmigoDTO) {
        List<TipoAmigoDTO> tipoamigosDTO = new ArrayList<>();
        List<TipoAmigo> tipoAmigos = tipoAmigoService.getTipoAmigosByFiltro(filtroTipoAmigoDTO);
        if(tipoAmigos.size() > 0){
            for (TipoAmigo g:
             tipoAmigos) {
                TipoAmigoDTO tipoAmigoDTO = tipoAmigoMapper.toDTO(g);
                tipoamigosDTO.add(tipoAmigoDTO);
            }
        }

        return ResponseEntity.ok(tipoamigosDTO);
    }

    /**
     * Retorna uma lista de {@link TipoAmigoDTO} cadastrados.
     *
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Retorna uma lista de TipoAmigos cadastrados.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoAmigoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getTipoAmigos() {
        List<TipoAmigo> tipoAmigos = tipoAmigoService.getTodos();
        List<TipoAmigoDTO> tipoAmigosDTO = new ArrayList<>();
        for (TipoAmigo tipoAmigo : tipoAmigos) {
            TipoAmigoDTO tipoAmigoDTO = tipoAmigoMapper.toDTO(tipoAmigo);
            tipoAmigosDTO.add(tipoAmigoDTO);
        }
        return ResponseEntity.ok(tipoAmigosDTO);
    }

    /**
     * Ativa o {@link Grupo} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_TIPOAMIGO_REMOVER')")
    @ApiOperation(value = "Remove um TipoAmigo pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoAmigoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> remover(@ApiParam(value = "Id do TipoAmigo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        TipoAmigo tipoAmigo = tipoAmigoService.remover(id.longValue());
        return ResponseEntity.ok(tipoAmigo);
    }

}
