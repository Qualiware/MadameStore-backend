package br.ueg.modelo.application.model;

import br.ueg.modelo.application.configuration.Constante;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "TBL_TIPO_PRODUTO", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_TIPO_PRODUTO", sequenceName = "TBL_S_TIPO_PRODUTO", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class TipoProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_TIPO_PRODUTO")
    @Column(name = "ID_TIPO_PRODUTO", nullable = false)
    private Long id;

    @Column(name = "NOME_TIPO_PRODUTO", length = 100, nullable = false)
    private String nome;

}
