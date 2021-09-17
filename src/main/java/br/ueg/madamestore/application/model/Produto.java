package br.ueg.madamestore.application.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import br.ueg.madamestore.application.configuration.Constante;
import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.enums.converter.StatusSimNaoConverter;

import java.time.LocalDate;

@Entity
@Table(name = "TBL_PRODUTO", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_PRODUTO", sequenceName = "TBL_S_PRODUTO", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_PRODUTO")
    @Column(name = "ID_PRODUTO", nullable = false)
    private Long id;

    @Column(name = "NOME_PRODUTO", length = 100, nullable = false)
    private String nome;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_PRODUTO", referencedColumnName = "ID_TIPO_PRODUTO", nullable = false)
    private TipoProduto tipo;

    @Column(name="PECO_PRODUTO", nullable = false)
    private Double preco;

    @Column(name = "DATA_CADASTRO",nullable = false)
    private LocalDate dataCadastro;

    @Column(name = "QUANTIDADE", nullable = false)
    private Integer quantidade;

    @Column(name = "V_LIQUIDO", nullable = false)
    private Double vliquido;

    @Column(name = "DESCRICAO", length = 1000)
    private String descricao;

    @Column(name = "QUANTIDADE_VENDIDA")
    private Integer quantidadeVendida;

    //@Column(name = "DATA_ATUALIZADO")
    //private LocalDate dataAtualizada;

   /* @Convert(converter = StatusSimNaoConverter.class)
    @Column(name = "E_AMIGO", length = 1, nullable = false)
    private StatusSimNao amigo;*/

}
