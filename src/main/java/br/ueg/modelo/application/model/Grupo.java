package br.ueg.modelo.application.model;

import br.ueg.modelo.application.enums.StatusAtivoInativo;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.enums.converter.StatusAtivoInativoConverter;
import br.ueg.modelo.application.enums.converter.StatusSimNaoConverter;
import br.ueg.modelo.application.configuration.Constante;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "TBL_GRUPO_USUARIO", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode
@SequenceGenerator(name = "TBL_S_GRUPO_USUARIO", sequenceName = "TBL_S_GRUPO_USUARIO", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Grupo implements Serializable {
    private static final long serialVersionUID = -8899975090870463525L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_GRUPO_USUARIO")
    @Column(name = "ID_GRUPO_USUARIO", nullable = false)
    private Long id;

    @Column(name = "NOME_GRUPO_USUARIO", length = 50)
    private String nome;

    @Column(name = "DESC_GRUPO_USUARIO", length = 200)
    private String descricao;

    @Convert(converter = StatusAtivoInativoConverter.class)
    @Column(name = "STAT_GRUPO_USUARIO", nullable = false, length = 1)
    private StatusAtivoInativo status;

    @Convert(converter = StatusSimNaoConverter.class)
    @Column(name = "FLAG_GRUPO_ADMIN_USUARIO", nullable = false, length = 1)
    private StatusSimNao administrador;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GrupoFuncionalidade> grupoFuncionalidades;
}
