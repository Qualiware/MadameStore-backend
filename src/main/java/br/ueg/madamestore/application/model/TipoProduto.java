package br.ueg.madamestore.application.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import br.ueg.madamestore.application.configuration.Constante;

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

    @Column(name = "VALOR", length = 100, nullable=true)
    private Integer valor;

}
