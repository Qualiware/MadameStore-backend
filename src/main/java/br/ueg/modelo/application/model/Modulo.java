package br.ueg.modelo.application.model;

import br.ueg.modelo.application.enums.StatusAtivoInativo;
import br.ueg.modelo.application.enums.converter.StatusAtivoInativoConverter;
import br.ueg.modelo.application.configuration.Constante;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TBL_MODULO_SISTEMA", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_MODULO_SISTEMA", sequenceName = "TBL_S_MODULO_SISTEMA", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Modulo  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_MODULO_SISTEMA")
    @Column(name = "ID_MODULO_SISTEMA", nullable = false)
    private Long id;

    @Column(name = "NOME_MODULO_SISTEMA", length = 200, nullable = false)
    private String nome;

    @Column(name = "CODG_MNEMONICO_MODULO", length = 40, nullable = false)
    private String mnemonico;

    @Convert(converter = StatusAtivoInativoConverter.class)
    @Column(name = "STAT_MODULO_SISTEMA", nullable = false, length = 1)
    private StatusAtivoInativo status;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "modulo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Funcionalidade> funcionalidades;

}
