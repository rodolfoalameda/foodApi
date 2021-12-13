package com.project.food.controller;

import com.project.food.controller.assembler.EstadoDTOAssembler;
import com.project.food.controller.assembler.EstadoInputDisassembler;
import com.project.food.domain.exception.EstadoNaoEncontradaException;
import com.project.food.domain.exception.NegocioException;
import com.project.food.domain.model.Estado;
import com.project.food.domain.model.dtos.EstadoDTO;
import com.project.food.domain.model.input.EstadoInput;
import com.project.food.domain.service.EstadoService;
import com.project.food.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoDTOAssembler estadoDTOAssembler;

    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @GetMapping("/{id}")
    public EstadoDTO findById(@PathVariable Long id) {
        Estado estado = estadoService.findOrFail(id);
        return estadoDTOAssembler.fromDomainToDto(estado);
    }

    @GetMapping
    public List<EstadoDTO> findAll() {
        List<Estado> list = estadoRepository.findAll();
        return estadoDTOAssembler.fromDomainsToDtos(list);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        estadoService.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO save(@RequestBody @Valid EstadoInput estadoInput) {
        try {
            Estado estado = estadoInputDisassembler.fromModelToDomain(estadoInput);
            return estadoDTOAssembler.fromDomainToDto(estadoService.save(estado));
        } catch (EstadoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public EstadoDTO edit(@PathVariable Long id, @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoAtual = estadoService.findOrFail(id);
        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);
        return estadoDTOAssembler.fromDomainToDto(estadoService.save(estadoAtual));
    }

}
