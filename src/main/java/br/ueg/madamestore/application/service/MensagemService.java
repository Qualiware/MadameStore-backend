/*
 * UsuarioService.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.service;

import br.ueg.madamestore.application.dto.FiltroMensagemDTO;
import br.ueg.madamestore.application.dto.FiltroVendaDTO;
import br.ueg.madamestore.application.dto.VendaDTO;
import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.exception.SistemaMessageCode;
import br.ueg.madamestore.application.model.*;
import br.ueg.madamestore.application.repository.*;
import br.ueg.madamestore.comum.exception.BusinessException;
import br.ueg.madamestore.comum.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Classe de négocio referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MensagemService {

	@Autowired
	private MensagemRepository mensagemRepository;

	@Autowired
	private ProdutoRepository produtoRepository;


	@Autowired
	private AuthService authService;

    /**
     * Persiste os dados do {@link Mensagem}.
     *
     * @param mensagem
     * @return
     */


	public Mensagem salvar(Mensagem mensagem) {



		//validaTotalQuantidade(venda);
		buscarProduto(mensagem);
		validarCamposObrigatorios(mensagem);
		mensagem= mensagemRepository.save(mensagem);
		mensagem = mensagemRepository.findByIdFetch(mensagem.getId()).get();
		return mensagem;
	}





	public void retiraQuantidade(Mensagem mensagem){
	//	configurarVendaProduto(venda);
		//buscarProduto(venda);
		//System.out.println(venda.getItemVenda().toString()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

			Produto produto;

			produto= produtoRepository.getOne(mensagem.getProduto().getId());
			if(produto==null){
				throw new BusinessException(SistemaMessageCode.ERRO_PRODUTO_NAO_ENCONTRADO);
			}
			else{
				if(produto.getQuantidadeVendida()==null){
					produto.setQuantidadeVendida(0);
				}
				produto.setQuantidade(produto.getQuantidade()-mensagem.getQuantidade());

			}
			mensagem.setProduto(produto);


	}



	private void buscarProduto(Mensagem mensagem) {


			Produto produto;
			produto= produtoRepository.getOne(mensagem.getProduto().getId());
			if(produto==null){
				throw new BusinessException(SistemaMessageCode.ERRO_PRODUTO_NAO_ENCONTRADO);
			}
			mensagem.setProduto(produto);


	}





    /**
     * Verifica se os campos obrigatorios de {@link Venda} foram preenchidos.
     *
     * @param mensagem
     */
	private void validarCamposObrigatorios(final Mensagem mensagem) {
		boolean invalido = Boolean.FALSE;

		if (mensagem.getDataAlteracao()== null) {
			invalido = Boolean.TRUE;
		}

		if (mensagem.getProduto() == null)
			invalido = Boolean.TRUE;

		if (mensagem.getQuantidade() == null)
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
	public List<Mensagem> getMensagemByFiltro(FiltroMensagemDTO filtroDTO) {
		validarCamposObrigatoriosFiltro(filtroDTO);

		List<Mensagem> mensagem = mensagemRepository.findAllByFiltro(filtroDTO);

		if (CollectionUtil.isEmpty(mensagem)) {
			throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
		}

		return mensagem;
	}

	public void retira(Mensagem mensagem){
		Produto produto;

		produto= produtoRepository.getOne(mensagem.getProduto().getId());
		if(produto==null){
			throw new BusinessException(SistemaMessageCode.ERRO_PRODUTO_NAO_ENCONTRADO);
		}

		if(produto.getQuantidadeVendida()==null && produto.getQuantidade()> mensagem.getQuantidade()){
			produto.setQuantidadeVendida(0);
			produto.setQuantidade(produto.getQuantidade()-mensagem.getQuantidade());
		}

	}

	public void add(Mensagem mensagem){

			Produto produto;

			produto= produtoRepository.getOne(mensagem.getProduto().getId());
			if(produto==null){
				throw new BusinessException(SistemaMessageCode.ERRO_PRODUTO_NAO_ENCONTRADO);
			}

		if(produto.getQuantidadeVendida()==null){
			produto.setQuantidadeVendida(0);
			produto.setQuantidade(produto.getQuantidade()+mensagem.getQuantidade());
		}





	}

	public Mensagem remover(Long id){
		Mensagem mensagem = this.getById(id);

		mensagemRepository.delete(mensagem);

		return mensagem;
	}

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Grupo, verifica se tem pelo meno 4 caracteres.
     *
     * @param filtroDTO
     */
	private void validarCamposObrigatoriosFiltro(final FiltroMensagemDTO filtroDTO) {
		boolean vazio = Boolean.TRUE;
		if (filtroDTO.getDataAlteracao()!=null) {
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
	public List<Mensagem> getProdutosByFiltro(final FiltroMensagemDTO filtroDTO) {
		validarCamposObrigatoriosFiltro(filtroDTO);

		List<Mensagem> mensagem = mensagemRepository.findAllByFiltro(filtroDTO);

		if (CollectionUtil.isEmpty(mensagem)) {
			throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
		}

		return mensagem;
	}




	/**
     * Retorna a instância de {@link Venda} conforme o 'id' informado.
     *
     * @param id -
     * @return -
     */
	public Mensagem getById(final Long id) {
		return mensagemRepository.findById(id).orElse(null);
	}

	/**
	 * Retorna a instância de {@link Usuario} conforme o 'id' informado.
	 * 
	 * @param id
	 * @return
	 */
	public Mensagem getByIdFetch(final Long id) {
		return mensagemRepository.findByIdFetch(id).orElse(null);
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
