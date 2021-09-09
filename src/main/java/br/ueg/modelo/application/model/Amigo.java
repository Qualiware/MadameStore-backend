package br.ueg.modelo.application.model;

import br.ueg.modelo.application.configuration.Constante;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.enums.converter.StatusSimNaoConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TBL_AMIGO", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_AMIGO", sequenceName = "TBL_S_AMIGO", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Amigo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_AMIGO")
    @Column(name = "ID_AMIGO", nullable = false)
    private Long id;

    @Column(name = "NOME_AMIGO", length = 100, nullable = false)
    private String nome;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_AMIGO", referencedColumnName = "ID_TIPO_AMIGO", nullable = false)
    private TipoAmigo tipo;

    @Column(name = "DATA_ATUALIZADO")
    private LocalDate dataAmizade;

    @Convert(converter = StatusSimNaoConverter.class)
    @Column(name = "E_AMIGO", length = 1, nullable = false)
    private StatusSimNao amigo;

}
