package br.ueg.modelo.application.controller;

import br.ueg.modelo.api.util.Validation;
import br.ueg.modelo.application.dto.FiltroTipoProdutoDTO;
import br.ueg.modelo.application.dto.TipoAmigoDTO;
import br.ueg.modelo.application.dto.TipoProdutoDTO;
import br.ueg.modelo.application.mapper.TipoAmigoMapper;
import br.ueg.modelo.application.mapper.TipoProdutoMapper;
import br.ueg.modelo.application.model.Grupo;
import br.ueg.modelo.application.model.TipoAmigo;
import br.ueg.modelo.application.model.TipoProduto;
import br.ueg.modelo.application.service.TipoAmigoService;
import br.ueg.modelo.application.service.TipoProdutoService;
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

@Api(tags = "TipoProduto API")
@RestController
@RequestMapping(path = "${app.api.base}/tipo-produto")
public class TipoProdutoController extends AbstractController {

    @Autowired
    private TipoProdutoMapper tipoProdutoMapper;

    @Autowired
    private TipoProdutoService tipoProdutoService;

    @PreAuthorize("hasRole('ROLE_TIPOAMIGO_INCLUIR')")
    @PostMapping
    @ApiOperation(value = "Inclusão/alteração de tipo produto.",
            notes = "Incluir/Alterar Tipo Produto.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoProdutoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações de Tipo Produto", required = true) @Valid @RequestBody TipoProdutoDTO tipoProdutoDTO) {
            TipoProduto grupo = tipoProdutoMapper.toEntity(tipoProdutoDTO);
                  //  grupo = tipoProdutoMapper.toEntity(tipoProdutoDTO);
            return ResponseEntity.ok(tipoProdutoMapper.toDTO(tipoProdutoService.salvar(grupo)));
    }

    /**
     * Altera a instância de {@link TipoProdutoDTO} na base de dados.
     *
     * @param id
     * @param tipoProdutoDTO
     * @return
     */
    @PreAuthorize("hasRole('ROLE_TIPOAMIGO_ALTERAR')")
    @ApiOperation(value = "Altera as informações de Tipo Produto.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoProdutoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(
            @ApiParam(value = "Código do Tipo Produto", required = true) @PathVariable final BigDecimal id,
            @ApiParam(value = "Informações do Tipo Produto", required = true) @Valid @RequestBody TipoProdutoDTO tipoProdutoDTO) {
        Validation.max("id", id, 99999999L);
        TipoProduto tipoProduto = tipoProdutoMapper.toEntity(tipoProdutoDTO);
        tipoProduto.setId(id.longValue());
        TipoProduto tipoProdutoSaved = tipoProdutoService.salvar(tipoProduto);
        return ResponseEntity.ok(tipoProdutoMapper.toDTO(tipoProdutoSaved));
    }

    /**
     * Retorna a instância de {@link TipoProdutoDTO} pelo id informado.
     *
     * @param id
     * s@return
     */
    @PreAuthorize("hasRole('ROLE_TIPOAMIGO_PESQUISAR')")
    @ApiOperation(value = "Retorna as informações do TipoProduto pelo id informado.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoProdutoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@ApiParam(value = "Código do Grupo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        TipoProduto tipoProduto = tipoProdutoService.getById(id.longValue());
        TipoProdutoDTO tipoProdutoDTO = tipoProdutoMapper.toDTO(tipoProduto);

        return ResponseEntity.ok(tipoProdutoDTO);
    }

    /**
     * Retorna a buscar de {@link TipoProduto} por {@link FiltroTipoProdutoDTO}
     *
     * @param filtroTipoProdutoDTO
     * @return
     */
    @PreAuthorize("hasRole('ROLE_TIPOAMIGO_PESQUISAR')")
    @ApiOperation(value = "Pesquisa de TipoProduto.",
            notes = "Recupera as informações de TipoProduto conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoProdutoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroTipoProdutoDTO filtroTipoProdutoDTO) {
        List<TipoProdutoDTO> tipoprodutosDTO = new ArrayList<>();
        List<TipoProduto> tipoProdutos = tipoProdutoService.getTipoProdutosByFiltro(filtroTipoProdutoDTO);
        if(tipoProdutos.size() > 0){
            for (TipoProduto g:
             tipoProdutos) {
                TipoProdutoDTO tipoProdutoDTO = tipoProdutoMapper.toDTO(g);
                tipoprodutosDTO.add(tipoProdutoDTO);
            }
        }

        return ResponseEntity.ok(tipoprodutosDTO);
    }

    /**
     * Retorna uma lista de {@link TipoProdutoDTO} cadastrados.
     *
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Retorna uma lista de TipoProdutos cadastrados.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoProdutoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getTipoProdutos() {
        List<TipoProduto> tipoProdutos = tipoProdutoService.getTodos();
        List<TipoProdutoDTO> tipoProdutosDTO = new ArrayList<>();
        for (TipoProduto tipoProduto : tipoProdutos) {
            TipoProdutoDTO tipoProdutoDTO = tipoProdutoMapper.toDTO(tipoProduto);
            tipoProdutosDTO.add(tipoProdutoDTO);
        }
        return ResponseEntity.ok(tipoProdutosDTO);
    }

    /**
     * Ativa o {@link Grupo} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_TIPOAMIGO_REMOVER')")
    @ApiOperation(value = "Remove um TipoProduto pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TipoProdutoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> remover(@ApiParam(value = "Id do TipoProduto", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        TipoProduto tipoProduto = tipoProdutoService.remover(id.longValue());
        return ResponseEntity.ok(tipoProduto);
    }

}
