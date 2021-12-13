package com.project.food.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "TB_PRODUTO")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "PRECO")
    private BigDecimal preco;

    @Column(name = "ATIVO")
    private Boolean ativo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

}
