package br.ueg.madamestore.application.model;

import br.ueg.madamestore.application.configuration.Constante;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TBL_CLIENTE", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_CLIENTE", sequenceName = "TBL_S_CLIENTE", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_CLIENTE")
    @Column(name = "ID_CLIENTE", nullable = false)
    private Long id;

    @Column(name = "NOME_CLIENTE", length = 100, nullable = false)
    private String nome;

    @Column(name = "EMAIL_CLIENTE", length = 100, nullable = false)
    private String email;

    @Column(name = "TELEFONE_CLIENTE", length = 100, nullable = false)
    private String telefone;

    @Column(name = "DATA_CADASTRO",nullable = false)
    private LocalDate dataCadastro;

    @Column(name = "QUANTIDADE", nullable = true)
    private Integer quantidade;


    //@Column(name = "DATA_ATUALIZADO")
    //private LocalDate dataAtualizada;

   /* @Convert(converter = StatusSimNaoConverter.class)
    @Column(name = "E_AMIGO", length = 1, nullable = false)
    private StatusSimNao amigo;*/

}
