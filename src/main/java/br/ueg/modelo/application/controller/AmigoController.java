package br.ueg.modelo.application.controller;

import br.ueg.modelo.api.util.Validation;
import br.ueg.modelo.application.dto.AmigoDTO;
import br.ueg.modelo.application.dto.FiltroAmigoDTO;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.mapper.AmigoMapper;
import br.ueg.modelo.application.model.Amigo;
import br.ueg.modelo.application.service.AmigoService;
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

@Api(tags = "Amigo API")
@RestController
@RequestMapping(path = "${app.api.base}/amigo")
public class AmigoController extends AbstractController {

    @Autowired
    private AmigoMapper amigoMapper;

    @Autowired
    private AmigoService amigoService;

    @PreAuthorize("hasRole('ROLE_AMIGO_INCLUIR')")
    @PostMapping
    @ApiOperation(value = "Inclusão de amigo.",
            notes = "Incluir Amigo.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = AmigoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações de Amigo", required = true) @Valid @RequestBody AmigoDTO amigoDTO) {
            Amigo grupo = amigoMapper.toEntity(amigoDTO);
            return ResponseEntity.ok(amigoMapper.toDTO(amigoService.salvar(grupo)));
    }

    /**
     * Altera a instância de {@link AmigoDTO} na base de dados.
     *
     * @param id
     * @param amigoDTO
     * @return
     */
    @PreAuthorize("hasRole('ROLE_AMIGO_ALTERAR')")
    @ApiOperation(value = "Altera as informações de Amigo.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = AmigoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(
            @ApiParam(value = "Código do Amigo", required = true) @PathVariable final BigDecimal id,
            @ApiParam(value = "Informações do Amigo", required = true) @Valid @RequestBody AmigoDTO amigoDTO) {
        Validation.max("id", id, 99999999L);
        Amigo amigo = amigoMapper.toEntity(amigoDTO);
        amigo.setId(id.longValue());
        Amigo amigoSaved = amigoService.salvar(amigo);
        return ResponseEntity.ok(amigoMapper.toDTO(amigoSaved));
    }

    /**
     * Retorna a instância de {@link AmigoDTO} pelo id informado.
     *
     * @param id
     * s@return
     */
    @PreAuthorize("hasRole('ROLE_AMIGO_PESQUISAR')")
    @ApiOperation(value = "Retorna as informações do Amigo pelo id informado.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = AmigoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@ApiParam(value = "Código do Amigo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Amigo amigo = amigoService.getById(id.longValue());
        AmigoDTO amigoDTO = amigoMapper.toDTO(amigo);

        return ResponseEntity.ok(amigoDTO);
    }

    /**
     * Retorna a buscar de {@link Amigo} por {@link FiltroAmigoDTO}
     *
     * @param filtroAmigoDTO
     * @return
     */
    @PreAuthorize("hasRole('ROLE_AMIGO_PESQUISAR')")
    @ApiOperation(value = "Pesquisa de Amigo.",
            notes = "Recupera as informações de Amigo conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = AmigoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroAmigoDTO filtroAmigoDTO) {
        List<AmigoDTO> amigosDTO = new ArrayList<>();
        List<Amigo> amigos = amigoService.getAmigosByFiltro(filtroAmigoDTO);
        if(amigos.size() > 0){
            for (Amigo g:
             amigos) {
                AmigoDTO amigoDTO = amigoMapper.toDTO(g);
                amigosDTO.add(amigoDTO);
            }
        }

        return ResponseEntity.ok(amigosDTO);
    }

    /**
     * Retorna uma lista de {@link AmigoDTO} cadastrados.
     *
     * @return
     */
    @PreAuthorize("hasRole('ROLE_AMIGO_PESQUISAR')")
    @ApiOperation(value = "Retorna uma lista de Amigos cadastrados.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = AmigoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAmigos() {
        List<Amigo> amigos = amigoService.getTodos();
        List<AmigoDTO> amigosDTO = new ArrayList<>();
        for (Amigo amigo : amigos) {
            AmigoDTO amigoDTO = amigoMapper.toDTO(amigo);
            amigosDTO.add(amigoDTO);
        }
        return ResponseEntity.ok(amigosDTO);
    }

    /**
     * Remover o {@link Amigo} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_AMIGO_REMOVER')")
    @ApiOperation(value = "Remove um Amigo pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = AmigoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> remover(@ApiParam(value = "Id do Amigo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Amigo amigo = amigoService.remover(id.longValue());
        return ResponseEntity.ok(amigoMapper.toDTO(amigo));
    }

    /**
     * Tornar Amigo do {@link Amigo} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_AMIGO_STATUS')")
    @ApiOperation(value = "Tonar Amigo do Amigo pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = AmigoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/tornar-amigo", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> tonarAmigo(@ApiParam(value = "Id do Amigo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Amigo amigo = amigoService.getById(id.longValue());
        amigo.setAmigo(StatusSimNao.SIM);
        amigoService.salvar(amigo);
        return ResponseEntity.ok(amigoMapper.toDTO(amigo));
    }

    /**
     * Deixar de Ser amigo Amigo do {@link Amigo} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_AMIGO_STATUS')")
    @ApiOperation(value = "Deixar de ser Amigo do Amigo pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = AmigoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/deixar-amigo", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> deixarAmigo(@ApiParam(value = "Id do Amigo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Amigo amigo = amigoService.getById(id.longValue());
        amigo.setAmigo(StatusSimNao.NAO);
        amigoService.salvar(amigo);
        return ResponseEntity.ok(amigoMapper.toDTO(amigo));
    }

}
