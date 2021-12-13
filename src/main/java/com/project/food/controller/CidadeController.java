package com.project.food.controller;

import com.project.food.controller.assembler.CidadeDTOAssembler;
import com.project.food.controller.assembler.CidadeInputDisassembler;
import com.project.food.domain.exception.EntidadeNaoEncontradaException;
import com.project.food.domain.exception.NegocioException;
import com.project.food.domain.model.Cidade;
import com.project.food.domain.model.dtos.CidadeDTO;
import com.project.food.domain.model.input.CidadeInput;
import com.project.food.domain.service.CidadeService;
import com.project.food.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeDTOAssembler cidadeDTOAssembler;

    @Autowired
    CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping("/{id}")
    public CidadeDTO findById(@PathVariable Long id) {
        Cidade cidade = cidadeService.findOrFail(id);
        return cidadeDTOAssembler.fromDomainToDto(cidade);
    }

    @GetMapping
    public List<CidadeDTO> findAll() {
        List<Cidade> list = cidadeRepository.findAll();
        return cidadeDTOAssembler.fromDomainsToDtos(list);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cidadeService.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO save(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.fromModelToDomain(cidadeInput);
            return cidadeDTOAssembler.fromDomainToDto(cidadeService.save(cidade));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public CidadeDTO edit(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeAtual = cidadeService.findOrFail(id);
            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
            return cidadeDTOAssembler.fromDomainToDto(cidadeService.save(cidadeAtual));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
