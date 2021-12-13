package com.project.food.domain.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.food.domain.model.Restaurante;

import java.util.List;

public abstract class CozinhaMixin {

    @JsonIgnore
    private List<Restaurante> restaurantes;
}
