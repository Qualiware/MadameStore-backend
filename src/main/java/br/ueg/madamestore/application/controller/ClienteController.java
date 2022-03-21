package br.ueg.madamestore.application.controller;

import br.ueg.madamestore.api.util.Validation;
import br.ueg.madamestore.application.dto.FiltroClienteDTO;
import br.ueg.madamestore.application.dto.ClienteDTO;
import br.ueg.madamestore.application.dto.VendaDTO;
import br.ueg.madamestore.application.mapper.ClienteMapper;
import br.ueg.madamestore.application.model.Cliente;
import br.ueg.madamestore.application.model.Venda;
import br.ueg.madamestore.application.service.ClienteService;
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

@Api(tags = "Cliente API")
@RestController
@RequestMapping(path = "${app.api.base}/cliente")
public class ClienteController extends AbstractController {
    
    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private ClienteService clienteService;

    @PreAuthorize("hasRole('ROLE_CLIENTE_INCLUIR')")
    @PostMapping
    @ApiOperation(value = "Inclusão de cliente.",
            notes = "Incluir Cliente.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClienteDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações do Cliente", required = true) @Valid @RequestBody ClienteDTO clienteDTO) {
        
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());

        return ResponseEntity.ok(clienteMapper.toDTO(clienteService.salvar(cliente)));
    }

    /**
     * Altera a instância de {@link ClienteDTO} na base de dados.
     *  Permissões do MadameStore
     * @param id
     * @param clienteDTO
     * @return
     */
    @PreAuthorize("hasRole('ROLE_CLIENTE_ALTERAR')")
    @ApiOperation(value = "Altera as informações de Cliente.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClienteDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(
            @ApiParam(value = "Código do Cliente", required = true) @PathVariable final BigDecimal id,
            @ApiParam(value = "Informações do Cliente", required = true) @Valid @RequestBody ClienteDTO clienteDTO) {
        Validation.max("id", id, 99999999L);
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        cliente.setId(id.longValue());
        Cliente clienteSaved = clienteService.salvar(cliente);
        return ResponseEntity.ok(clienteMapper.toDTO(clienteSaved));
    }

    /**
     * Retorna a instância de {@link ClienteDTO} pelo id informado.
     *
     * @param id
     * s@return
     */
    @PreAuthorize("hasRole('ROLE_CLIENTE_PESQUISAR')")
    @ApiOperation(value = "Retorna as informações do Cliente pelo id informado.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClienteDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@ApiParam(value = "Código do Cliente", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Cliente cliente = clienteService.getById(id.longValue());
        ClienteDTO clienteDTO = clienteMapper.toDTO(cliente);

        return ResponseEntity.ok(clienteDTO);
    }

    /**
     * Retorna a buscar de {@link Cliente} por {@link FiltroClienteDTO}
     *
     * @param filtroClienteDTO
     * @return
     */
    @PreAuthorize("hasRole('ROLE_CLIENTE_PESQUISAR')")
    @ApiOperation(value = "Pesquisa de Cliente.",
            notes = "Recupera as informações de Cliente conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClienteDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroClienteDTO filtroClienteDTO) {
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        List<Cliente> clientes = clienteService.getClientesByFiltro(filtroClienteDTO);
        if(clientes.size() > 0){
            for (Cliente g:

             clientes) {
                ClienteDTO clienteDTO = clienteMapper.toDTO(g);
                clientesDTO.add(clienteDTO);
            }
        }

        return ResponseEntity.ok(clientesDTO);
    }

    /**
     * Retorna uma lista de {@link ClienteDTO} cadastrados.
     *
     * @return
     */
    @PreAuthorize("hasRole('ROLE_CLIENTE_PESQUISAR')")
    @ApiOperation(value = "Retorna uma lista de Clientes cadastrados.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClienteDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getClientes() {
        List<Cliente> clientes = clienteService.getTodos();
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        for (Cliente cliente : clientes) {
            ClienteDTO clienteDTO = clienteMapper.toDTO(cliente);
            clientesDTO.add(clienteDTO);
        }
        return ResponseEntity.ok(clientesDTO);
    }

    /**
     * Remover o {@link Cliente} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_CLIENTE_REMOVER')")
    @ApiOperation(value = "Remove um Cliente pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClienteDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> remover(@ApiParam(value = "Id do Cliente", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Cliente cliente = clienteService.remover(id.longValue());
        return ResponseEntity.ok(clienteMapper.toDTO(cliente));
    }

    /**
     * Tornar Cliente do {@link Cliente} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_CLIENTE_STATUS')")
    @ApiOperation(value = "Tonar Cliente do Cliente pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClienteDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/tornar-cliente", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> tonarCliente(@ApiParam(value = "Id do cliente", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Cliente cliente = clienteService.getById(id.longValue());
      //  cliente.setCliente(StatusSimNao.SIM);
        clienteService.salvar(cliente);
        return ResponseEntity.ok(clienteMapper.toDTO(cliente));
    }

   /* @PreAuthorize("hasRole('ROLE_CLIENTE_STATUS')")
    @ApiOperation(value = "Deixar de ser Cliente do Amigo pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
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
    }*/


    @ApiOperation(value = "Recupera os clientes pelo Filtro Informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = VendaDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(path = "/all", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAll() {
        List<Cliente> clientes = clienteService.getTodos();
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        if(clientes.size() > 0){
            for (Cliente g:

                    clientes) {
                ClienteDTO clienteDTO = clienteMapper.toDTO(g);
                clientesDTO.add(clienteDTO);
            }
        }

        return ResponseEntity.ok(clientesDTO);
    }

}
