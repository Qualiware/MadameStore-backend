package br.ueg.modelo.application.controller;

import br.ueg.modelo.api.util.Validation;
import br.ueg.modelo.application.dto.FiltroGrupoDTO;
import br.ueg.modelo.application.dto.FiltroUsuarioDTO;
import br.ueg.modelo.application.dto.GrupoDTO;
import br.ueg.modelo.application.mapper.GrupoMapper;
import br.ueg.modelo.application.model.Grupo;
import br.ueg.modelo.application.security.CredentialImpl;
import br.ueg.modelo.application.service.GrupoService;
import br.ueg.modelo.application.service.UsuarioGrupoService;
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

@Api(tags = "Grupo API")
@RestController
@RequestMapping(path = "${app.api.base}/grupos")
public class GrupoController extends AbstractController {

    @Autowired
    private GrupoMapper grupoMapper;

    @Autowired
    private UsuarioGrupoService usuarioGrupoService;

    @Autowired
    private GrupoService grupoService;

    /***
     * Recupera grupos vinculados ao usuário
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Recupera o grupo pelo usuário.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = GrupoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(path = "/user")
    public ResponseEntity<?> getUsuarioById() {
        CredentialImpl credential = getCredential();
        Long id = credential.getIdUsuario();
        List<GrupoDTO> gruposDTO = new ArrayList<>();
        List<Grupo> grupos = usuarioGrupoService.getUsuarioGrupos(id);
        for (Grupo grupo: grupos) {
            GrupoDTO grupoDTO = grupoMapper.toDTO(grupo);
            grupoDTO.setGrupoFuncionalidades(null);
            gruposDTO.add(grupoDTO);
        }
        return ResponseEntity.ok(gruposDTO);
    }

    @PreAuthorize("hasRole('ROLE_GRUPO_INCLUIR')")
    @PostMapping
    @ApiOperation(value = "Inclusão/alteração de grupo.",
            notes = "Incluir/Alterar grupo de acesso.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = GrupoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações de Grupo", required = true) @Valid @RequestBody GrupoDTO grupoDTO) {
            Grupo grupo = grupoMapper.toEntity(grupoDTO);
            return ResponseEntity.ok(grupoMapper.toDTO(grupoService.salvar(grupo)));
    }

    /**
     * Altera a instância de {@link GrupoDTO} na base de dados.
     *
     * @param id
     * @param grupoTO
     * @return
     */
    @PreAuthorize("hasRole('ROLE_GRUPO_ALTERAR')")
    @ApiOperation(value = "Altera as informações de Grupo.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = GrupoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(@ApiParam(value = "Código do Sistema", required = true) @PathVariable final BigDecimal id, @ApiParam(value = "Informações de Grupo", required = true) @Valid @RequestBody GrupoDTO grupoTO) {
        Validation.max("id", id, 99999999L);
        Grupo grupo = grupoMapper.toEntity(grupoTO);
        grupo.setId(id.longValue());
        Grupo grupoSaved = grupoService.salvar(grupo);
        return ResponseEntity.ok(grupoMapper.toDTO(grupoSaved));
    }

    /**
     * Retorna a instância de {@link GrupoDTO} pelo id informado.
     *
     * @param id
     * s@return
     */
    @PreAuthorize("hasRole('ROLE_GRUPO_PESQUISAR')")
    @ApiOperation(value = "Retorna as informações do Grupo pelo id informado.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = GrupoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@ApiParam(value = "Código do Grupo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Grupo grupo = grupoService.getGrupoByIdFetch(id.longValue());
        GrupoDTO grupoDTO = grupoMapper.toDTO(grupo);

        return ResponseEntity.ok(grupoDTO);
    }

    /**
     * Retorna a buscar de {@link Grupo} por {@link FiltroUsuarioDTO}
     *
     * @param filtroGrupoDTO
     * @return
     */
    @PreAuthorize("hasRole('ROLE_GRUPO_PESQUISAR')")
    @ApiOperation(value = "Pesquisa de grupos.",
            notes = "Recupera as informações de Grupo conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = GrupoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroGrupoDTO filtroGrupoDTO) {
        List<GrupoDTO> gruposDTO = new ArrayList<>();
        List<Grupo> grupos = grupoService.getGruposByFiltro(filtroGrupoDTO);
        if(grupos.size() > 0){
            for (Grupo g:
             grupos) {
                GrupoDTO grupoDTO = grupoMapper.toDTO(g);
                grupoDTO.setGrupoFuncionalidades(null);
                gruposDTO.add(grupoDTO);
            }
        }

        return ResponseEntity.ok(gruposDTO);
    }

	/**
	 * Retorna uma lista de {@link GrupoDTO} ativos conforme o 'id' do Sistema informado.
	 *
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@ApiOperation(value = "Retorna uma lista de Grupos ativos conforme o 'id' do Sistema informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Success", response = GrupoDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) 
	})
	@GetMapping(path = "/grupo/ativos", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getAtivos() {
		List<Grupo> grupos = grupoService.getAtivos();
		List<GrupoDTO> gruposDTO = new ArrayList<>();
		for (Grupo grupo : grupos) {
			grupo.setGrupoFuncionalidades(null);
			GrupoDTO grupoDTO = grupoMapper.toDTO(grupo);
			grupoDTO.setGrupoFuncionalidades(null);
			gruposDTO.add(grupoDTO);
		}
		return ResponseEntity.ok(gruposDTO);
	}

    /**
     * Retorna uma lista de {@link GrupoDTO} cadastrados.
     *
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Retorna uma lista de Grupos cadastrados.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = GrupoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getGrupos() {
        List<Grupo> grupos = grupoService.getCadastrados();
        List<GrupoDTO> gruposDTO = new ArrayList<>();
        for (Grupo grupo : grupos) {
            grupo.setGrupoFuncionalidades(null);
            GrupoDTO grupoDTO = grupoMapper.toDTO(grupo);
            grupoDTO.setGrupoFuncionalidades(null);
            gruposDTO.add(grupoDTO);
        }
        return ResponseEntity.ok(gruposDTO);
    }

    /**
     * Inativa o {@link Grupo} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_GRUPO_ATIVAR_INATIVAR')")
    @ApiOperation(value = "Inativa o Grupo pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/inativo", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> inativar(@ApiParam(value = "Id do Grupo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        grupoService.inativar(id.longValue());
        return ResponseEntity.ok().build();
    }

    /**
     * Ativa o {@link Grupo} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_GRUPO_ATIVAR_INATIVAR')")
    @ApiOperation(value = "Ativa o Grupo pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/ativo", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> ativar(@ApiParam(value = "Id do Grupo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        grupoService.ativar(id.longValue());
        return ResponseEntity.ok().build();
    }

}
