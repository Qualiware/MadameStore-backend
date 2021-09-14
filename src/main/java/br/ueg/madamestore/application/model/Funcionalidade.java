package br.ueg.madamestore.application.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import br.ueg.madamestore.application.configuration.Constante;
import br.ueg.madamestore.application.enums.StatusAtivoInativo;
import br.ueg.madamestore.application.enums.converter.StatusAtivoInativoConverter;

import java.io.Serializable;

@Entity
@Table(name = "TBL_FUNCIONALIDADE_MODULO", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_FUNC_MODULO", sequenceName = "TBL_S_FUNC_MODULO", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Funcionalidade implements Serializable {
    private static final long serialVersionUID = 4381258342853410159L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_FUNC_MODULO")
    @Column(name = "ID_FUNCIONALIDADE_MODULO", nullable = false)
    private Long id;

    @Column(name = "NOME_FUNCIONALIDADE_MODULO", length = 200, nullable = false)
    private String nome;

    @Column(name = "CODG_MNEMONICO_FUNCIONALIDADE", length = 40, nullable = false)
    private String mnemonico;

    @Convert(converter = StatusAtivoInativoConverter.class)
    @Column(name = "STAT_FUNCIONALIDADE_MODULO", nullable = false, length = 1)
    private StatusAtivoInativo status;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MODULO_SISTEMA", referencedColumnName = "ID_MODULO_SISTEMA", nullable = false)
    private Modulo modulo;
}
