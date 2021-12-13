package com.project.food.domain.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.food.domain.model.Estado;

public abstract class CidadeMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;
}
