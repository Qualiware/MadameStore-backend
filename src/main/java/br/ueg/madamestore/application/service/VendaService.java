/*
 * UsuarioService.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.service;

import br.ueg.madamestore.application.configuration.Constante;
import br.ueg.madamestore.application.dto.*;
import br.ueg.madamestore.application.enums.StatusAtivoInativo;
import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.exception.SistemaMessageCode;
import br.ueg.madamestore.application.model.*;
import br.ueg.madamestore.application.repository.ClienteRepository;
import br.ueg.madamestore.application.repository.ItemVendaRepository;
import br.ueg.madamestore.application.exception.SistemaMessageCode;
import br.ueg.madamestore.application.model.*;
import br.ueg.madamestore.application.repository.ClienteRepository;
import br.ueg.madamestore.application.repository.ProdutoRepository;
import br.ueg.madamestore.application.repository.VendaRepository;
import br.ueg.madamestore.comum.exception.BusinessException;
import br.ueg.madamestore.comum.util.CollectionUtil;
import br.ueg.madamestore.comum.util.Util;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.ueg.madamestore.application.exception.SistemaMessageCode.ERRO_QUANTIDADE_DE_PRODUTOS_INSUFICIENTE;

/**
 * Classe de négocio referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ItemVendaRepository itemVendaRepository;

	@Autowired
	private AuthService authService;

    /**
     * Persiste os dados do {@link Venda}.
     *
     * @param venda
     * @return
     */


	public Venda salvar(Venda venda) {

		if(venda.getId() == null && venda.getStatusEspera() == null){
			venda.setStatusEspera(StatusEspera.SIM);
		}

		if(venda.getId() == null && venda.getStatusVendido() == null){

			venda.setStatusVendido(StatusVendido.NAO);
		}

		configurarVendaProduto(venda);
		//validaTotalQuantidade(venda);
		buscarProduto(venda);
		buscarCliente(venda);
		validarCamposObrigatorios(venda);
		venda= vendaRepository.save(venda);
		venda = vendaRepository.findByIdFetch(venda.getId()).get();
		return venda;
	}

	public void aumentarQuantidadeVendida(Venda venda){

		for (ItemVenda itemVenda : venda.getItemVenda()) {
			Produto produto;

			produto= produtoRepository.getOne(itemVenda.getProduto().getId());
			if(produto==null){
				throw new BusinessException(SistemaMessageCode.ERRO_PRODUTO_NAO_ENCONTRADO);
			}
			else {
			}
			;
		}

	}
	public void retiraVendaAlterarQuantidade(VendaDTO venda){
		//configurarVendaProduto(venda);
		//for (ItemVenda itemVenda : venda.getItemVenda()) {
		//	Produto produto;

		//	produto= produtoRepository.getOne(itemVenda.getProduto().getId());
		//	if(produto==null){
		//		throw new BusinessException(SistemaMessageCode.ERRO_PRODUTO_NAO_ENCONTRADO);
		//	}
		//	else{

		//	}
		//	itemVenda.setProduto(produto);
		//}


	}


	public void retiraQuantidade(Venda venda){
		configurarVendaProduto(venda);
		//buscarProduto(venda);
		//System.out.println(venda.getItemVenda().toString()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		for (ItemVenda itemVenda : venda.getItemVenda()) {
			Produto produto;

			produto= produtoRepository.getOne(itemVenda.getProduto().getId());
			if(produto==null){
				throw new BusinessException(SistemaMessageCode.ERRO_PRODUTO_NAO_ENCONTRADO);
			}
			else{
				if(produto.getQuantidadeVendida()==null){
					produto.setQuantidadeVendida(0);
				}
				if(produto.getQuantidade()>=itemVenda.getQuantidadeVendida()){
					produto.setQuantidade(produto.getQuantidade()-itemVenda.getQuantidadeVendida());
					produto.setQuantidadeVendida(produto.getQuantidadeVendida()+itemVenda.getQuantidadeVendida());
				}

			}
			itemVenda.setProduto(produto);
		}

	}


	public void adicionarQuantidade(Venda venda){
		configurarVendaProduto(venda);
		//buscarProduto(venda);
		//System.out.println(venda.getItemVenda().toString()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		for (ItemVenda itemVenda : venda.getItemVenda()) {
			Produto produto;

			produto= produtoRepository.getOne(itemVenda.getProduto().getId());
			if(produto==null){
				throw new BusinessException(SistemaMessageCode.ERRO_PRODUTO_NAO_ENCONTRADO);
			}
			else{
				if(produto.getQuantidadeVendida()==null){
					produto.setQuantidadeVendida(0);
				}
				produto.setQuantidade(produto.getQuantidade()+itemVenda.getQuantidadeVendida());
				produto.setQuantidadeVendida(produto.getQuantidadeVendida()-itemVenda.getQuantidadeVendida());
			}
			itemVenda.setProduto(produto);
		}

	}

	private void validaTotalQuantidade(Venda venda) {
		Produto produto;
		//produto= produtoRepository.getOne(itemVenda.getProduto().getId());



		//if(produto.getQuantidade()<itemVenda.getProduto().getQuantidade()){
			//throw new BusinessException(ERRO_QUANTIDADE_DE_PRODUTOS_INSUFICIENTE);
		//}
	}

	private void buscarCliente(Venda venda) {
		Cliente cliente;
		cliente= clienteRepository.getOne(venda.getCliente().getId());

		if(cliente==null){
			throw new BusinessException(SistemaMessageCode.ERRO_CLIENTE_NAO_ENCONTRADO);
		}
		venda.setCliente(cliente);
	}

	private void buscarProduto(Venda venda) {

		for (ItemVenda itemVenda : venda.getItemVenda()) {
			Produto produto;

			produto= produtoRepository.getOne(itemVenda.getProduto().getId());
			if(produto==null){
				throw new BusinessException(SistemaMessageCode.ERRO_PRODUTO_NAO_ENCONTRADO);
			}
			itemVenda.setProduto(produto);

		}
	}

	public void adicionaValoresProduto(Venda venda) {
		configurarVendaProduto(venda);
		//System.out.println(venda.getItemVenda()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		for (ItemVenda itemVenda : venda.getItemVenda()) {
			Produto produto;

			produto = produtoRepository.getOne(itemVenda.getProduto().getId());
			if (produto == null) {
				throw new BusinessException(SistemaMessageCode.ERRO_PRODUTO_NAO_ENCONTRADO);
			} else {
				if (produto.getQuantidadeVendida() == null) {
					produto.setQuantidadeVendida(0);
				}
				System.out.println("ENTROU 1111111111");
				produto.setQuantidade(produto.getQuantidade()+itemVenda.getQuantidadeVendida());
				produto.setQuantidadeVendida(produto.getQuantidadeVendida()-itemVenda.getQuantidadeVendida());
			}
			itemVenda.setProduto(produto);
		}
		vendaRepository.save(venda);

	}


	/**
	 * Configura o {@link Venda} dentro de  {@link TelefoneUsuario} para salvar.
	 * 
	 * @param venda
*/
	public void configurarVendaProduto(Venda venda) {


		for (ItemVenda itemVenda : venda.getItemVenda()){
			itemVenda.setVenda(venda);
		}


	}



    /**
     * Verifica se os campos obrigatorios de {@link Venda} foram preenchidos.
     *
     * @param venda
     */
	private void validarCamposObrigatorios(final Venda venda) {
		boolean invalido = Boolean.FALSE;

		if (venda.getDataVenda()== null) {
			invalido = Boolean.TRUE;
		}

		if (venda.getItemVenda() == null)
			invalido = Boolean.TRUE;

		if (venda.getValorTotal() == null)
			invalido = Boolean.TRUE;

		if (venda.getStatusVendido() == null)
			invalido = Boolean.TRUE;

		if (venda.getStatusEspera() == null)
			invalido = Boolean.TRUE;



		if (invalido) {
			throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
		}
	}



    /**
     * Retorna a Lista de {@link VendaDTO} conforme o filtro pesquisado.
     * 
     * @param filtroDTO
     * @return
     */
	public List<Venda> getVendaByFiltro(FiltroVendaDTO filtroDTO) {
		validarCamposObrigatoriosFiltro(filtroDTO);

		List<Venda> venda = vendaRepository.findAllByFiltro(filtroDTO);

		if (CollectionUtil.isEmpty(venda)) {
			throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
		}

		return venda;
	}

	public Venda remover(Long id){
		Venda venda = this.getById(id);

		configurarVendaProduto(venda);
		buscarProduto(venda);
		buscarCliente(venda);
		adicionarQuantidade(venda);
		vendaRepository.delete(venda);


		return venda;
	}

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Grupo, verifica se tem pelo meno 4 caracteres.
     *
     * @param filtroDTO
     */
	private void validarCamposObrigatoriosFiltro(final FiltroVendaDTO filtroDTO) {
		boolean vazio = Boolean.TRUE;



		if (filtroDTO.getDataVenda()!=null) {
			vazio = Boolean.FALSE;
		}

		if (filtroDTO.getIdVenda()!=null) {
			vazio = Boolean.FALSE;
		}

		if (filtroDTO.getValorTotal()!=null) {
			vazio = Boolean.FALSE;
		}

		if (filtroDTO.getStatusVendido()!=null) {
			vazio = Boolean.FALSE;
		}

		if (filtroDTO.getStatusEspera()!=null) {
			vazio = Boolean.FALSE;
		}




		if (vazio) {
			throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
		}
	}



	/**
	 * Retorna uma lista de {@link Venda} conforme o filtro de pesquisa informado.
	 *
	 * @param filtroDTO
	 * @return
	 */
	public List<Venda> getProdutosByFiltro(final FiltroVendaDTO filtroDTO) {
		validarCamposObrigatoriosFiltro(filtroDTO);

		List<Venda> venda = vendaRepository.findAllByFiltro(filtroDTO);

		if (CollectionUtil.isEmpty(venda)) {
			throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
		}

		return venda;
	}




	/**
     * Retorna a instância de {@link Venda} conforme o 'id' informado.
     *
     * @param id -
     * @return -
     */
	public Venda getById(final Long id) {
		return vendaRepository.findById(id).orElse(null);
	}

	/**
	 * Retorna a instância de {@link Usuario} conforme o 'id' informado.
	 * 
	 * @param id
	 * @return
	 */
	public Venda getByIdFetch(final Long id) {
		return vendaRepository.findByIdFetch(id).orElse(null);
	}



	/**
	 * Retorna a instância de {@link Usuario} conforme o 'cpf' informado.
	 * 
	 * @param cpf
	 * @return

	public Usuario findByCpfUsuario(final String cpf) {
		return vendaRepository.findByCpf(cpf);
	}
	 */

	/**
	 * Retorna a instância do {@link Usuario} conforme o 'cpf' informado
	 * e que não tenha o 'id' informado.
	 * 
	 * @param cpf
	 * @param id
	 * @return

	public Usuario findByCpfUsuarioAndNotId(final String cpf, final Long id) {
		return vendaRepository.findByCpfAndNotId(cpf, id);
	}
	 */




}
