package br.ueg.madamestore.application.controller;

import br.ueg.madamestore.application.dto.ClienteDTO;
import br.ueg.madamestore.application.dto.VendaDTO;
import br.ueg.madamestore.application.model.Cliente;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.ueg.madamestore.api.util.Validation;
import br.ueg.madamestore.application.dto.FiltroProdutoDTO;
import br.ueg.madamestore.application.dto.ProdutoDTO;
import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.mapper.AmigoMapper;
import br.ueg.madamestore.application.mapper.ProdutoMapper;
import br.ueg.madamestore.application.model.Amigo;
import br.ueg.madamestore.application.model.Produto;
import br.ueg.madamestore.application.service.AmigoService;
import br.ueg.madamestore.application.service.ProdutoService;
import br.ueg.madamestore.comum.exception.MessageResponse;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "Produto API")
@RestController
@RequestMapping(path = "${app.api.base}/produto")
public class ProdutoController extends AbstractController {

    @Autowired
    private ProdutoMapper produtoMapper;

    @Autowired
    private ProdutoService produtoService;

    @PreAuthorize("hasRole('ROLE_PRODUTO_INCLUIR')")
    @PostMapping
    @ApiOperation(value = "Inclusão de produto.",
            notes = "Incluir Produto.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ProdutoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações do Produto", required = true) @Valid @RequestBody ProdutoDTO produtoDTO) {
            Produto grupo = produtoMapper.toEntity(produtoDTO);
            return ResponseEntity.ok(produtoMapper.toDTO(produtoService.salvar(grupo)));
    }

    /**
     * Altera a instância de {@link ProdutoDTO} na base de dados.
     *  Permissões do MadameStore
     * @param id
     * @param produtoDTO
     * @return
     */
    @PreAuthorize("hasRole('ROLE_PRODUTO_ALTERAR')")
    @ApiOperation(value = "Altera as informações de Produto.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ProdutoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(
            @ApiParam(value = "Código do Produto", required = true) @PathVariable final BigDecimal id,
            @ApiParam(value = "Informações do Produto", required = true) @Valid @RequestBody ProdutoDTO produtoDTO) {
        Validation.max("id", id, 99999999L);
        Produto produto = produtoMapper.toEntity(produtoDTO);
        produto.setId(id.longValue());
        Produto produtoSaved = produtoService.salvar(produto);
        return ResponseEntity.ok(produtoMapper.toDTO(produtoSaved));
    }

    /**
     * Retorna a instância de {@link ProdutoDTO} pelo id informado.
     *
     * @param id
     * s@return
     */
    @PreAuthorize("hasRole('ROLE_PRODUTO_PESQUISAR')")
    @ApiOperation(value = "Retorna as informações do Produto pelo id informado.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ProdutoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@ApiParam(value = "Código do Produto", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Produto produto = produtoService.getById(id.longValue());
        ProdutoDTO produtoDTO = produtoMapper.toDTO(produto);

        return ResponseEntity.ok(produtoDTO);
    }

    /**
     * Retorna a buscar de {@link Produto} por {@link FiltroProdutoDTO}
     *
     * @param filtroProdutoDTO
     * @return
     */
    @PreAuthorize("hasRole('ROLE_PRODUTO_PESQUISAR')")
    @ApiOperation(value = "Pesquisa de Produto.",
            notes = "Recupera as informações de Produto conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ProdutoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroProdutoDTO filtroProdutoDTO) {
        List<ProdutoDTO> produtosDTO = new ArrayList<>();
        List<Produto> produtos = produtoService.getProdutosByFiltro(filtroProdutoDTO);
        if(produtos.size() > 0){
            for (Produto g:

             produtos) {
                ProdutoDTO produtoDTO = produtoMapper.toDTO(g);
                produtosDTO.add(produtoDTO);
            }
        }

        return ResponseEntity.ok(produtosDTO);
    }

    /**
     * Retorna uma lista de {@link ProdutoDTO} cadastrados.
     *
     * @return
     */
    @PreAuthorize("hasRole('ROLE_PRODUTO_PESQUISAR')")
    @ApiOperation(value = "Retorna uma lista de Produtos cadastrados.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ProdutoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(path = "/ativos", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getProdutos() {
        List<Produto> produtos = produtoService.getTodos();
        List<ProdutoDTO> produtosDTO = new ArrayList<>();
        for (Produto produto : produtos) {
            ProdutoDTO produtoDTO = produtoMapper.toDTO(produto);
            produtosDTO.add(produtoDTO);
        }
        return ResponseEntity.ok(produtosDTO);
    }

    /**
     * Remover o {@link Produto} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_PRODUTO_REMOVER')")
    @ApiOperation(value = "Remove um Produto pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ProdutoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> remover(@ApiParam(value = "Id do Produto", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Produto produto = produtoService.remover(id.longValue());
        return ResponseEntity.ok(produtoMapper.toDTO(produto));
    }

    /**
     * Tornar Produto do {@link Produto} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_PRODUTO_STATUS')")
    @ApiOperation(value = "Tonar Produto do Produto pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ProdutoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/tornar-produto", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> tonarProduto(@ApiParam(value = "Id do produto", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Produto produto = produtoService.getById(id.longValue());
      //  produto.setProduto(StatusSimNao.SIM);
        produtoService.salvar(produto);
        return ResponseEntity.ok(produtoMapper.toDTO(produto));
    }


    // ESTATISTICS
    @ApiOperation(value = "Retorna uma lista dos produtos mais vendidos.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ProdutoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/estatisticas/ranking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRanking() {
        List<Produto> produtos = produtoService.getAllDesc();

        return ResponseEntity.ok(produtos);
    }

    @ApiOperation(value = "Recupera os produtos pelo Filtro Informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(path = "/all", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAll() {
        List<Produto> produtos = produtoService.getTodos();
        List<ProdutoDTO> produtosDTO = new ArrayList<>();
        if(produtos.size() > 0){
            for (Produto g:

                    produtos) {
                ProdutoDTO produtoDTO = produtoMapper.toDTO(g);
                produtosDTO.add(produtoDTO);
            }
        }

        return ResponseEntity.ok(produtosDTO);
    }

}
