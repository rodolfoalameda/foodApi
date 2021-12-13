package com.project.food.controller;

import com.project.food.controller.assembler.CozinhaDTOAssembler;
import com.project.food.controller.assembler.CozinhaInputDisassembler;
import com.project.food.domain.exception.CozinhaNaoEncontradaException;
import com.project.food.domain.exception.EntidadeNaoEncontradaException;
import com.project.food.domain.model.Cozinha;
import com.project.food.domain.model.dtos.CozinhaDTO;
import com.project.food.domain.model.input.CozinhaInput;
import com.project.food.domain.service.CozinhaService;
import com.project.food.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CozinhaInputDisassembler cozinhaInputDisassembler;

    @Autowired
    private CozinhaDTOAssembler cozinhaDTOAssembler;

    @GetMapping("/{id}")
    public CozinhaDTO findById(@PathVariable Long id) {
        Cozinha cozinha = cozinhaService.findOrFail(id);
        return cozinhaDTOAssembler.fromDomainToDto(cozinha);
    }

    @GetMapping
    public Page<CozinhaDTO> findAll(Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
        List<CozinhaDTO> cozinhaDTOS = cozinhaDTOAssembler.fromDomainsToDtos(cozinhasPage.getContent());
        return new PageImpl<>(cozinhaDTOS, pageable, cozinhasPage.getTotalElements());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cozinhaService.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO save(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaInputDisassembler.fromModelToDomain(cozinhaInput);
        return cozinhaDTOAssembler.fromDomainToDto(cozinhaService.save(cozinha));
    }

    @PutMapping("/{id}")
    public CozinhaDTO edit(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) {
        try {
            Cozinha cozinhaAtual = cozinhaService.findOrFail(id);
            cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
            return cozinhaDTOAssembler.fromDomainToDto(cozinhaService.save(cozinhaAtual));
        } catch (EntidadeNaoEncontradaException e) {
            throw new CozinhaNaoEncontradaException(e.getMessage());
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Cozinha>> findByName(@RequestParam String nome) {
        List<Cozinha> list = cozinhaRepository.findByNomeContaining(nome);
        return ResponseEntity.ok(list);
    }

}
