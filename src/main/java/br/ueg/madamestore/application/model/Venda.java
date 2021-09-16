package br.ueg.madamestore.application.model;

import br.ueg.madamestore.application.configuration.Constante;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TBL_VENDA", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_VENDA", sequenceName = "TBL_S_VENDA", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_VENDA")
    @Column(name = "ID_VENDA", nullable = false)
    private Long id;


    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "venda", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Produto> produtos;


    @Column(name="VALORTOTAL", nullable = false)
    private Double valorTotal;

    @Column(name = "DATA_VENDA",nullable = false)
    private LocalDate dataVenda;

    @Column(name="QUANTIDADE", nullable = false)
    private Integer quantidade;

    //@Column(name = "DATA_ATUALIZADO")
    //private LocalDate dataAtualizada;

   /* @Convert(converter = StatusSimNaoConverter.class)
    @Column(name = "E_AMIGO", length = 1, nullable = false)
    private StatusSimNao amigo;*/

}
