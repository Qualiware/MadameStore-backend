package br.ueg.modelo.application.service;

import br.ueg.modelo.application.dto.FiltroGrupoDTO;
import br.ueg.modelo.application.enums.StatusAtivoInativo;
import br.ueg.modelo.application.model.Grupo;
import br.ueg.modelo.application.model.GrupoFuncionalidade;
import br.ueg.modelo.comum.exception.BusinessException;
import br.ueg.modelo.comum.util.CollectionUtil;
import br.ueg.modelo.comum.util.Util;
import br.ueg.modelo.application.configuration.Constante;
import br.ueg.modelo.application.exception.SistemaMessageCode;
import br.ueg.modelo.application.repository.GrupoFuncionalidadeRepository;
import br.ueg.modelo.application.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoFuncionalidadeRepository grupoFuncionalidadeRepository;

    /**
     * Retorna uma lista de {@link Grupo} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Grupo> getGruposByFiltro(final FiltroGrupoDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Grupo> grupos = grupoRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(grupos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return grupos;
    }

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Grupo, verifica se tem pelo meno 4 caracteres.
     *
     * @param filtroDTO
     */
    private void validarCamposObrigatoriosFiltro(final FiltroGrupoDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getNome())) {
            vazio = Boolean.FALSE;

            if (filtroDTO.getNome().trim().length() < Integer.parseInt(Constante.TAMANHO_MINIMO_PESQUISA_NOME)) {
                throw new BusinessException(SistemaMessageCode.ERRO_TAMANHO_INSUFICIENTE_FILTRO_GRUPO);
            }
        }

        if (!Util.isEmpty(filtroDTO.getIdStatus())) {
            vazio = Boolean.FALSE;
        }
        if(filtroDTO.getIdModulo()!=null && filtroDTO.getIdModulo()>0){
            vazio = Boolean.FALSE;

        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }

    /**
	 * Retorna uma lista de {@link Grupo} ativos .
	 *
	 * @return
	 */
	public List<Grupo> getAtivos() {
		List<Grupo> grupos = grupoRepository.getAtivos();

		if (CollectionUtil.isEmpty(grupos)) {
			throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
		}
		return grupos;
	}

    /**
     * Retorna uma lista de {@link Grupo} cadatrados .
     *
     * @return
     */
    public List<Grupo> getCadastrados() {
        List<Grupo> grupos = grupoRepository.findAll();

        if (CollectionUtil.isEmpty(grupos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return grupos;
    }

    /**
     * Salva a instânica de {@link Grupo} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param grupo
     * @return
     */
    public Grupo salvar(Grupo grupo) {
        validarCamposObrigatorios(grupo);
        validarDuplicidade(grupo);

        tratarGrupoFuncionalidade(grupo);

        Grupo grupoSaved = grupoRepository.save(grupo);
        grupoSaved = grupoRepository.findByIdFetch(grupoSaved.getId());
        return grupoSaved;
    }

     /**
     * Realiza o tratamento do atributo grupo funcionalidade (preenchimento do atributo grupo e
     * tratar os gruposfuncionalidades não alterados
     * @param grupo
     */
    private void tratarGrupoFuncionalidade(Grupo grupo) {
        Set<GrupoFuncionalidade> grupoFuncionalidadesToDB = new HashSet<>();

        for (GrupoFuncionalidade grupoFuncionalidade : grupo.getGrupoFuncionalidades()) {
            if (grupoFuncionalidade.getId() == null) {
                grupoFuncionalidade.setGrupo(grupo);
                grupoFuncionalidadesToDB.add(grupoFuncionalidade);
            } else {
                GrupoFuncionalidade grupoFuncionalidadeBD =
                        grupoFuncionalidadeRepository.findByIdFetch(grupoFuncionalidade.getId());
                grupoFuncionalidadesToDB.add(grupoFuncionalidadeBD);
            }
        }
        grupo.setGrupoFuncionalidades(grupoFuncionalidadesToDB);
    }

    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param grupo
     */
    private void validarCamposObrigatorios(final Grupo grupo) {
        boolean vazio = Boolean.FALSE;

        if (Util.isEmpty(grupo.getNome())) {
            vazio = Boolean.TRUE;
        }

        if (Util.isEmpty(grupo.getDescricao())) {
            vazio = Boolean.TRUE;
        }

        if (Util.isEmpty(grupo.getStatus().toString())) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

    /**
     * Verifica se o Grupo a ser salvo já existe na base de dados.
     *
     * @param grupo
     */
    private void validarDuplicidade(final Grupo grupo) {
        Long count = grupoRepository.countByNomeAndGrupoAndNotId(grupo.getNome(),
                grupo.getId());

        if (count > BigDecimal.ZERO.longValue()) {
            throw new BusinessException(SistemaMessageCode.ERRO_GRUPO_DUPLICADO);
        }
    }

    /**
     * Retorna os roles do sistema conforme o 'idUsuario' e o 'mnemonico'
     * informados.
     *
     * @param idUsuario
     * @return
     */
    public List<String> getRolesByUsuario(final Long idUsuario) {
        return grupoRepository.findRolesByUsuario(idUsuario);
    }

    /**
     * Inativa o {@link Grupo} pelo 'id'.
     *
     * @param id
     * @return
     */
    public Grupo inativar(final Long id) {
        Grupo grupo = getGrupoByIdFetch(id);
        grupo.setStatus(StatusAtivoInativo.INATIVO);
        return grupoRepository.save(grupo);
    }

    /**
     * Ativa o {@link Grupo} pelo 'id'.
     *
     * @param id
     * @return
     */
    public Grupo ativar(final Long id) {
        Grupo grupo = getGrupoByIdFetch(id);
        grupo.setStatus(StatusAtivoInativo.ATIVO);
        return grupoRepository.save(grupo);
    }

    /**
     * Retorna a instância de {@link Grupo} conforme o 'id' informado.
     *
     * @param id
     * @return
     */
    public Grupo getGrupoByIdFetch(final Long id) {
        return grupoRepository.findByIdFetch(id);
    }
}
