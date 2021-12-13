package com.project.food.controller.assembler;

import com.project.food.domain.model.Restaurante;
import com.project.food.domain.model.dtos.RestauranteDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteDTO fromDomainToDto(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteDTO.class);
    }

    public List<RestauranteDTO> fromDomainsToDtos(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> fromDomainToDto(restaurante))
                .collect(Collectors.toList());
    }

}
