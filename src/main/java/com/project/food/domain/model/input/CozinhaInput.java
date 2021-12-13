package com.project.food.domain.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CozinhaInput {

    @NotNull
    private String nome;

}
