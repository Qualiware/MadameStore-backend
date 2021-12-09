/*
 * UsuarioServiceTest.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.portal.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.ueg.madamestore.application.dto.FiltroUsuarioDTO;
import br.ueg.madamestore.application.dto.UsuarioDTO;
import br.ueg.madamestore.application.dto.UsuarioSenhaDTO;
import br.ueg.madamestore.application.enums.StatusAtivoInativo;
import br.ueg.madamestore.application.model.TelefoneUsuario;
import br.ueg.madamestore.application.model.Usuario;
import br.ueg.madamestore.application.model.UsuarioGrupo;
import br.ueg.madamestore.application.repository.UsuarioRepository;
import br.ueg.madamestore.application.service.EmailEngineService;
import br.ueg.madamestore.application.service.UsuarioService;
import br.ueg.madamestore.comum.exception.BusinessException;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Implementação teste referente a classe de négocio {@link UsuarioService}.
 *
 * @author UEG
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class UsuarioServiceTest {

	@Mock
	private UsuarioRepository usuarioRepository;


	@Mock
	private EmailEngineService emailService;

	@InjectMocks
	private UsuarioService usuarioService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Teste do método que persiste os dados do {@link Usuario}.
	 * Caso em que os campos obrigatórios não foram preenchidos.
	 */
	@Test(expected = BusinessException.class)
	public void salvarCamposObrigatoriosNaoPreenchidos() {
		Usuario usuario = new Usuario();
		usuarioService.salvar(usuario);
	}

	/**
	 * Teste do método que persiste os dados do {@link Usuario}.
	 * Caso em que existe mais de um Usuário com o CPF informado.
	 */
	@Test(expected = BusinessException.class)
	public void salvarUsuarioDuplicadoPorCpf() {
		Usuario usuario = getUsuarioNovoMock();
		when(usuarioRepository.countByCpf(usuario.getCpf())).thenReturn(2L);
		usuarioService.salvar(usuario);
	}

	/**
	 * Teste do método que persiste os dados do {@link Usuario}.
	 * Caso em que o Usuário não é encontrado no AD.
	 */
	@Test(expected = BusinessException.class)
	public void salvarInclusaoUsuarioNaoEncontradoAD() {
		Usuario usuario = getUsuarioNovoMock();
		usuario.setId(null);

		when(usuarioRepository.countByCpf(usuario.getCpf())).thenReturn(0L);
		usuarioService.salvar(usuario);
	}

	/**
	 * Teste do método que persiste os dados do {@link Usuario}.
	 * Caso de Inclusão em que o Código do KeyCloak já está cadastrado.
	 */
	@Test(expected = BusinessException.class)
	public void salvarInclusaoLoginDuplicado() {
		Usuario usuario = getUsuarioNovoMock();
		usuario.setId(null);

		when(usuarioRepository.countByCpf(usuario.getCpf())).thenReturn(0L);
		usuarioService.salvar(usuario);
	}

	/**
	 * Teste do método que persiste os dados do {@link Usuario}.
	 * Caso de sucesso em que o Usuário é incluído.
	 */
	@Test
	public void salvarInclusao() {
		Usuario usuario = getUsuarioNovoMock();
		usuario.setId(null);

		when(usuarioRepository.countByCpf(usuario.getCpf())).thenReturn(0L);
		usuarioService.salvar(usuario);

		String nome = usuario.getNome();
		assertEquals(nome, usuario.getNome());
		assertTrue(usuario.isStatusAtivo());
		assertEquals(LocalDate.now(), usuario.getDataAtualizado());
		assertEquals(LocalDate.now(), usuario.getDataCadastrado());
	}

	/**
	 * Teste do método que persiste os dados do {@link Usuario}.
	 * Caso de sucesso em que o Usuário é alterado.
	 */
	@Test
	public void salvarAlteracao() {
		Usuario novo = getUsuarioNovoMock();
		Usuario vigente = getUsuarioVigenteMock();

		when(usuarioRepository.countByCpf(novo.getCpf())).thenReturn(0L);
		when(usuarioRepository.findById(novo.getId())).thenReturn(Optional.of(vigente));
		when(usuarioRepository.save(novo)).thenReturn(novo);
		Usuario retorno = usuarioService.salvar(novo);

		Assert.assertEquals(vigente.getStatus(), retorno.getStatus());
		assertEquals(vigente.getDataCadastrado(), retorno.getDataCadastrado());
		assertEquals(LocalDate.now(), retorno.getDataAtualizado());
	}

	/**
	 * Teste do método que configura o {@link Usuario} dentro de
	 * {@link UsuarioGrupo} e {@link TelefoneUsuario} para salvar.
	 */
	@Test
	public void configurarUsuarioGruposAndTelefones() {
		Usuario usuario = getUsuarioNovoMock();
		usuarioService.configurarUsuarioGruposAndTelefones(usuario);

		UsuarioGrupo grupo = usuario.getGrupos().stream().findFirst().orElse(null);
		assertEquals(usuario, grupo.getUsuario());

		TelefoneUsuario telefone = usuario.getTelefones().stream().findFirst().orElse(null);
		assertEquals(usuario, telefone.getUsuario());
	}

	/**
	 * Teste do método que retorna a instância do {@link Usuario} conforme o 'login' informado.
	 */
	@Test
	public void getByEmail() {
		String email = "user.email";
		Usuario usuario = getUsuarioNovoMock();

		when(usuarioRepository.findByEmail(email)).thenReturn(usuario);
		Usuario retorno = usuarioService.getByEmail(email);
		assertEquals(usuario, retorno);
	}

	/**
	 * Teste do método que retorna a lista de {@link UsuarioDTO} conforme o filtro pesquisado.
	 * Caso em que nenhum campo de pesquisa foi preenchido.
	 */
	@Test(expected = BusinessException.class)
	public void getUsuariosByFiltroNenhumCampoPreenchido() {
		FiltroUsuarioDTO filtroDTO = new FiltroUsuarioDTO();
		usuarioService.getUsuariosByFiltro(filtroDTO);
	}

	/**
	 * Teste do método que retorna a lista de {@link UsuarioDTO} conforme o filtro pesquisado.
	 * Caso em que o campo "Nome" tem menos de quatro caracteres.
	 */
	@Test(expected = BusinessException.class)
	public void getUsuariosByFiltroTamanhoInsuficienteNome() {
		FiltroUsuarioDTO filtroDTO = new FiltroUsuarioDTO();
		filtroDTO.setNome("ABC");
		usuarioService.getUsuariosByFiltro(filtroDTO);
	}

	/**
	 * Teste do método que retorna a lista de {@link UsuarioDTO} conforme o filtro pesquisado.
	 * Caso em que nenhum registro é encontrado.
	 */
	@Test(expected = BusinessException.class)
	public void getUsuariosByFiltroNenhumRegistroEncontrado() {
		FiltroUsuarioDTO filtroDTO = getFiltroUsuarioDTOMock();

		when(usuarioRepository.findAllByFiltro(filtroDTO)).thenReturn(new ArrayList<Usuario>());
		usuarioService.getUsuariosByFiltro(filtroDTO);
	}

	/**
	 * Teste do método que retorna a lista de {@link UsuarioDTO} conforme o filtro pesquisado.
	 * Caso de Sucesso.
	 */
	@Test
	public void getUsuariosByFiltro() {
		FiltroUsuarioDTO filtroDTO = getFiltroUsuarioDTOMock();

		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(getUsuarioNovoMock());

		when(usuarioRepository.findAllByFiltro(filtroDTO)).thenReturn(usuarios);
		usuarioService.getUsuariosByFiltro(filtroDTO);
	}

	/**
	 * Teste do método que registra o último acesso do {@link Usuario}.
	 */
	@Test
	public void salvarUltimoAcesso() {
		Usuario usuario = getUsuarioNovoMock();
		usuarioService.salvarUltimoAcesso(usuario);
		assertEquals(LocalDate.now(), usuario.getUltimoAcesso());
	}

	/**
	 * Teste do método que retorna a instância de {@link Usuario} conforme o 'id' informado.
	 */
	@Test
	public void getByIdFetch() {
		Long id = 1L;
		Usuario usuario = getUsuarioNovoMock();

		when(usuarioRepository.findByIdFetch(id)).thenReturn(Optional.of(usuario));
		Usuario retorno = usuarioService.getByIdFetch(id);
		assertEquals(usuario, retorno);
	}

	/**
	 * Teste do método que realiza a inclusão ou alteração de senha do {@link Usuario}.
	 * Caso em que o Usuário é vinculado ao AD, por isso a senha não pode ser alterada.
	 */
	@Test(expected = BusinessException.class)
	public void redefinirSenhaUsuarioAD() {
		UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO();
		Usuario usuario = getUsuarioNovoMock();
		when(usuarioRepository.findById(usuarioSenhaDTO.getIdUsuario())).thenReturn(Optional.of(usuario));
		usuarioService.redefinirSenha(usuarioSenhaDTO);
	}

	/**
	 * Teste do método que realiza a inclusão ou alteração de senha do {@link Usuario}.
	 * Caso em que os campos obrigatórios não foram preenchidos.
	 */
	@Test(expected = BusinessException.class)
	public void redefinirSenhaCamposobrigatoriosNaoPreenchidos() {
		UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO();

		Usuario usuario = getUsuarioNovoMock();
		when(usuarioRepository.findById(usuarioSenhaDTO.getIdUsuario())).thenReturn(Optional.of(usuario));
		usuarioService.redefinirSenha(usuarioSenhaDTO);
	}

	/**
	 * Teste do método que realiza a inclusão ou alteração de senha do {@link Usuario}.
	 * Caso em que os campos obrigatórios não foram preenchidos.
	 */
	@Test(expected = BusinessException.class)
	public void redefinirSenhaCamposobrigatoriosNaoPreenchidos2() {
		UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO();
		usuarioSenhaDTO.setNovaSenha("password");

		Usuario usuario = getUsuarioNovoMock();
		when(usuarioRepository.findById(usuarioSenhaDTO.getIdUsuario())).thenReturn(Optional.of(usuario));
		usuarioService.redefinirSenha(usuarioSenhaDTO);
	}

	/**
	 * Teste do método que realiza a inclusão ou alteração de senha do {@link Usuario}.
	 * Caso em que as confirmação de senha não confere com a senha.
	 */
	@Test(expected = BusinessException.class)
	public void redefinirSenhaConfirmacaoNaoConfere() {
		UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO();
		usuarioSenhaDTO.setNovaSenha("password");
		usuarioSenhaDTO.setConfirmarSenha("otherPassword");

		Usuario usuario = getUsuarioNovoMock();
		when(usuarioRepository.findById(usuarioSenhaDTO.getIdUsuario())).thenReturn(Optional.of(usuario));
		usuarioService.redefinirSenha(usuarioSenhaDTO);
	}

	/**
	 * Teste do método que realiza a inclusão ou alteração de senha do {@link Usuario}.
	 * Caso de Alteração em que a Senha Antiga não foi preenchida.
	 */
	@Test(expected = BusinessException.class)
	public void redefinirSenhaAlteracaoSenhaAntigaNaoPreenchida() {
		UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO();
		usuarioSenhaDTO.setNovaSenha("password");
		usuarioSenhaDTO.setConfirmarSenha("password");
		usuarioSenhaDTO.setSenhaAntiga("");
		usuarioSenhaDTO.setTipo(UsuarioSenhaDTO.TipoRedefinicaoSenha.alteracao);

		Usuario usuario = getUsuarioNovoMock();
		when(usuarioRepository.findById(usuarioSenhaDTO.getIdUsuario())).thenReturn(Optional.of(usuario));
		usuarioService.redefinirSenha(usuarioSenhaDTO);
	}

	/**
	 * Teste do método que realiza a inclusão ou alteração de senha do {@link Usuario}.
	 * Caso de Alteração em que a Senha Antiga está incorreta.
	 */
	@Test(expected = BusinessException.class)
	public void redefinirSenhaAlteracaoSenhaAntigaIncorreta() {
		UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO();
		usuarioSenhaDTO.setNovaSenha("password");
		usuarioSenhaDTO.setConfirmarSenha("password");
		usuarioSenhaDTO.setSenhaAntiga("oldPassword");
		usuarioSenhaDTO.setTipo(UsuarioSenhaDTO.TipoRedefinicaoSenha.alteracao);

		Usuario usuario = getUsuarioNovoMock();
		when(usuarioRepository.findById(usuarioSenhaDTO.getIdUsuario())).thenReturn(Optional.of(usuario));
		//when(keycloakService.loginByPassword(usuario.getLogin(), usuarioSenhaDTO.getSenhaAntiga())).thenReturn(null);
		usuarioService.redefinirSenha(usuarioSenhaDTO);
	}

	/**
	 * Teste do método que realiza a inclusão ou alteração de senha do {@link Usuario}.
	 * Caso em que a senha é alterada com sucesso.
	 */
	@Test
	public void redefinirSenhaAlteracao() {
		UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO();
		usuarioSenhaDTO.setNovaSenha("password");
		usuarioSenhaDTO.setConfirmarSenha("password");
		usuarioSenhaDTO.setSenhaAntiga("oldPassword");
		usuarioSenhaDTO.setTipo(UsuarioSenhaDTO.TipoRedefinicaoSenha.alteracao);


		Usuario usuario = getUsuarioNovoMock();
		when(usuarioRepository.findById(usuarioSenhaDTO.getIdUsuario())).thenReturn(Optional.of(usuario));
		//when(keycloakService.loginByPassword(usuario.getLogin(), usuarioSenhaDTO.getSenhaAntiga())).thenReturn(access);
		when(usuarioRepository.save(usuario)).thenReturn(usuario);
		Usuario retorno = usuarioService.redefinirSenha(usuarioSenhaDTO);
		assertEquals(usuario, retorno);
	}

	/**
	 * Teste do método que realiza a inclusão ou alteração de senha do {@link Usuario}.
	 * Caso em que a senha é ativada com sucesso.
	 */
	@Test
	public void redefinirSenhaAtivacao() {
		UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO();
		usuarioSenhaDTO.setNovaSenha("password");
		usuarioSenhaDTO.setConfirmarSenha("password");
		usuarioSenhaDTO.setTipo(UsuarioSenhaDTO.TipoRedefinicaoSenha.ativacao);

		Usuario usuario = getUsuarioNovoMock();
		when(usuarioRepository.findById(usuarioSenhaDTO.getIdUsuario())).thenReturn(Optional.of(usuario));
		when(usuarioRepository.save(usuario)).thenReturn(usuario);
		Usuario retorno = usuarioService.redefinirSenha(usuarioSenhaDTO);
		assertEquals(usuario, retorno);
		assertTrue(retorno.isStatusAtivo());
	}

	/**
	 * Teste do método que realiza a inclusão ou alteração de senha do {@link Usuario}.
	 * Caso em que a senha é recuperada com sucesso.
	 */
	@Test
	public void redefinirSenhaRecuperacao() {
		UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO();
		usuarioSenhaDTO.setNovaSenha("password");
		usuarioSenhaDTO.setConfirmarSenha("password");
		usuarioSenhaDTO.setTipo(UsuarioSenhaDTO.TipoRedefinicaoSenha.recuperacao);

		Usuario usuario = getUsuarioNovoMock();
		when(usuarioRepository.findById(usuarioSenhaDTO.getIdUsuario())).thenReturn(Optional.of(usuario));
		when(usuarioRepository.save(usuario)).thenReturn(usuario);
		Usuario retorno = usuarioService.redefinirSenha(usuarioSenhaDTO);
		assertEquals(usuario, retorno);
		assertTrue(retorno.isStatusAtivo());
	}

	/**
	 * Teste do método que retorna a instância de {@link Usuario} conforme o 'cpf' informado.
	 */
	@Test
	public void findByCpfUsuario() {
		String cpf = "65357952094";
		Usuario usuario = getUsuarioNovoMock();

		when(usuarioRepository.findByCpf(cpf)).thenReturn(usuario);
		Usuario retorno = usuarioService.findByCpfUsuario(cpf);
		assertEquals(usuario, retorno);
	}

	/**
	 * Teste do método que retorna a instância do {@link Usuario} conforme o 'cpf' informado
	 * e que não tenha o 'id' informado.
	 */
	@Test
	public void findByCpfUsuarioAndNotId() {
		Long id = 1L;
		String cpf = "65357952094";
		Usuario usuario = getUsuarioNovoMock();

		when(usuarioRepository.findByCpfAndNotId(cpf, id)).thenReturn(usuario);
		Usuario retorno = usuarioService.findByCpfUsuarioAndNotId(cpf, id);
		assertEquals(usuario, retorno);
	}

	/**
	 * Teste do método que solicita a recuperação de senha do {@link Usuario}.
	 * Caso em que o Usuário não é encontrado.
	 */
	@Test(expected = BusinessException.class)
	public void recuperarSenhaUsuarioNaoEncontrado() {
		String cpf = "65357952094";
		when(usuarioRepository.findByCpf(cpf)).thenReturn(null);
		usuarioService.recuperarSenha(cpf);
	}

	/**
	 * Teste do método que solicita a recuperação de senha do {@link Usuario}.
	 * Caso em que a solicitação é feita com sucesso.
	 */
	@Test
	public void recuperarSenha() {
		String cpf = "65357952094";
		Usuario usuario = getUsuarioNovoMock();

		when(usuarioRepository.findByCpf(cpf)).thenReturn(usuario);
		Usuario retorno = usuarioService.recuperarSenha(cpf);
		assertEquals(usuario, retorno);
	}



	/**
	 * Teste do método que inativa o {@link Usuario}.
	 */
	@Test
	public void inativar() {
		Long id = 1L;
		Usuario usuario = getUsuarioNovoMock();

		when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
		when(usuarioRepository.save(usuario)).thenReturn(usuario);
		Usuario retorno = usuarioService.inativar(id);
		assertEquals(usuario, retorno);
		assertFalse(retorno.isStatusAtivo());
	}

	/**
	 * Teste do método que ativa o {@link Usuario}.
	 */
	@Test
	public void ativar() {
		Long id = 1L;
		Usuario usuario = getUsuarioNovoMock();

		when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
		when(usuarioRepository.save(usuario)).thenReturn(usuario);
		Usuario retorno = usuarioService.ativar(id);
		assertEquals(usuario, retorno);
		assertTrue(retorno.isStatusAtivo());
	}

	/**
	 * Teste do método que verifica se o CPF informado é válido e se está em uso.
	 * Caso em que o CPF não foi preenchido.
	 */
	@Test(expected = BusinessException.class)
	public void validarCpfNaoPreenchido() {
		String cpf = null;
		usuarioService.validarCpf(cpf);
	}

	/**
	 * Teste do método que verifica se o CPF informado é válido e se está em uso.
	 * Caso em que o CPF é inválido.
	 */
	@Test(expected = BusinessException.class)
	public void validarCpfInvalido() {
		String cpf = "12345678901";
		usuarioService.validarCpf(cpf);
	}

	/**
	 * Teste do método que verifica se o CPF informado é válido e se está em uso.
	 * Caso em que o CPF é válido e está em uso.
	 */
	@Test(expected = BusinessException.class)
	public void validarCpfEmUso() {
		String cpf = "65357952094";

		when(usuarioRepository.findByCpf(cpf)).thenReturn(getUsuarioNovoMock());
		usuarioService.validarCpf(cpf);
	}

	/**
	 * Teste do método que verifica se o CPF informado é válido e se está em uso.
	 * Caso em que o CPF é válido e não está em uso.
	 */
	@Test
	public void validarCpfValido() {
		String cpf = "65357952094";

		when(usuarioRepository.findByCpf(cpf)).thenReturn(null);
		usuarioService.validarCpf(cpf);
	}

	/**
	 * Teste do método que verifica se o CPF informado é válido e se está em uso.
	 * Caso em que o CPF é válido e está em uso.
	 */
	@Test
	public void validarCpfEmUsoNotId() {
		Long id = 1L;
		String cpf = "65357952094";

		when(usuarioRepository.findByCpf(cpf)).thenReturn(getUsuarioNovoMock());
		usuarioService.validarCpf(cpf, id);
	}

	/**
	 * Teste do método que verifica se o CPF informado é válido e se está em uso.
	 * Caso em que o CPF é válido e não está em uso.
	 */
	@Test
	public void validarCpfValidoNotId() {
		Long id = 1L;
		String cpf = "65357952094";

		when(usuarioRepository.findByCpf(cpf)).thenReturn(null);
		usuarioService.validarCpf(cpf, id);
	}

	/**
	 * Retorna a instância Mock de um novo {@link Usuario}.
	 * 
	 * @return
	 */
	private Usuario getUsuarioNovoMock() {
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setNome("Nome do Usuário");
		usuario.setCpf("12345678901");

		UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
		Set<UsuarioGrupo> grupos = new HashSet<UsuarioGrupo>();
		grupos.add(usuarioGrupo);

		TelefoneUsuario telefoneUsuario = new TelefoneUsuario();
		Set<TelefoneUsuario> telefones = new HashSet<TelefoneUsuario>();
		telefones.add(telefoneUsuario);

		usuario.setGrupos(grupos);
		usuario.setTelefones(telefones);

		return usuario;
	}

	/**
	 * Retorna a instância Mock de um {@link Usuario} vigente.
	 * 
	 * @return
	 */
	private Usuario getUsuarioVigenteMock() {
		Usuario usuario = new Usuario();
		usuario.setStatus(StatusAtivoInativo.ATIVO);
		usuario.setDataCadastrado(LocalDate.of(2020, 3, 4));

		UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
		Set<UsuarioGrupo> grupos = new HashSet<UsuarioGrupo>();
		grupos.add(usuarioGrupo);

		usuario.setGrupos(grupos);

		return usuario;
	}

	/**
	 * Retorna a instância Mock de {@link FiltroUsuarioDTO}.
	 * 
	 * @return
	 */
	private FiltroUsuarioDTO getFiltroUsuarioDTOMock() {
		FiltroUsuarioDTO filtroDTO = new FiltroUsuarioDTO();
		filtroDTO.setNome("Nome");
		filtroDTO.setCpf("user.cpf");
		filtroDTO.setIdStatus(StatusAtivoInativo.ATIVO.getId());
		return filtroDTO;
	}
}
