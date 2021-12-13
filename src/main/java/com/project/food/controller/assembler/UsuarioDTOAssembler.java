package com.project.food.controller.assembler;

import com.project.food.domain.model.Usuario;
import com.project.food.domain.model.dtos.UsuarioDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDTO fromDomainToDto(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public List<UsuarioDTO> fromDomainsToDtos(Collection<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> fromDomainToDto(usuario))
                .collect(Collectors.toList());
    }

}
