package br.ueg.madamestore.application.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import br.ueg.madamestore.application.configuration.Constante;

@Entity
@Table(name = "TBL_TIPO_AMIGO", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_TIPO_AMIGO", sequenceName = "TBL_S_TIPO_AMIGO", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class TipoAmigo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_TIPO_AMIGO")
    @Column(name = "ID_TIPO_AMIGO", nullable = false)
    private Long id;

    @Column(name = "NOME_TIPO_AMIGO", length = 100, nullable = false)
    private String nome;

}
