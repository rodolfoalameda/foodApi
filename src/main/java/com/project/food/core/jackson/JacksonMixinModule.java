package com.project.food.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.project.food.domain.model.Cidade;
import com.project.food.domain.model.Cozinha;
import com.project.food.domain.model.Restaurante;
import com.project.food.domain.model.mixin.CidadeMixin;
import com.project.food.domain.model.mixin.CozinhaMixin;
import com.project.food.domain.model.mixin.RestauranteMixin;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
    }

}
