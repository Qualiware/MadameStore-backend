package br.ueg.modelo.application.model;

import br.ueg.modelo.application.configuration.Constante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TBL_GRUPO_FUNCIONALIDADE", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "TBL_S_GRUPO_FUNCIONALIDADE", sequenceName = "TBL_S_GRUPO_FUNCIONALIDADE", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class GrupoFuncionalidade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_GRUPO_FUNCIONALIDADE")
    @Column(name = "ID_GRUPO_FUNCIONALIDADE", nullable = false)
    private Long id;

    //@EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO_USUARIO", referencedColumnName = "ID_GRUPO_USUARIO", nullable = false)
    private Grupo grupo;

    //@EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FUNCIONALIDADE_MODULO", referencedColumnName = "ID_FUNCIONALIDADE_MODULO", nullable = false)
    private Funcionalidade funcionalidade;

}
