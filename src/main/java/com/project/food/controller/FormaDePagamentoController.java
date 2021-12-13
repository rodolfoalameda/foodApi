package com.project.food.controller;

import com.project.food.controller.assembler.FormaDePagamentoDTOAssembler;
import com.project.food.controller.assembler.FormaDePagamentoInputDisassembler;
import com.project.food.domain.model.FormaPagamento;
import com.project.food.domain.model.dtos.FormaPagamentoDTO;
import com.project.food.domain.model.input.FormaDePagamentoInput;
import com.project.food.domain.service.FormaDePagamentoService;
import com.project.food.repository.FormaDePagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaDePagamentoController {

    @Autowired
    private FormaDePagamentoRepository formaDePagamentoRepository;

    @Autowired
    private FormaDePagamentoService formaDePagamentoService;

    @Autowired
    private FormaDePagamentoDTOAssembler formaDePagamentoDTOAssembler;

    @Autowired
    private FormaDePagamentoInputDisassembler formaDePagamentoInputDisassembler;

    @GetMapping
    public List<FormaPagamentoDTO> findAll() {
        List<FormaPagamento> list = formaDePagamentoRepository.findAll();
        return formaDePagamentoDTOAssembler.fromDomainsToDtos(list);
    }

    @GetMapping("/{id}")
    public FormaPagamentoDTO findById(@PathVariable Long id) {
        FormaPagamento formaPagamento = formaDePagamentoService.findOrFail(id);
        return formaDePagamentoDTOAssembler.fromDomainToDto(formaPagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO save(@RequestBody @Valid FormaDePagamentoInput formaDePagamentoInput) {
        FormaPagamento formaPagamento = formaDePagamentoInputDisassembler.fromModelToDomain(formaDePagamentoInput);
        formaPagamento = formaDePagamentoService.save(formaPagamento);
        return formaDePagamentoDTOAssembler.fromDomainToDto(formaPagamento);
    }

    @PutMapping("/{id}")
    public FormaPagamentoDTO update(@PathVariable Long id, @RequestBody @Valid FormaDePagamentoInput formaDePagamentoInput) {
        FormaPagamento formaPagamentoAtual = formaDePagamentoService.findOrFail(id);
        formaDePagamentoInputDisassembler.copyToDomainObject(formaDePagamentoInput, formaPagamentoAtual);
        formaPagamentoAtual = formaDePagamentoService.save(formaPagamentoAtual);
        return formaDePagamentoDTOAssembler.fromDomainToDto(formaPagamentoAtual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        formaDePagamentoService.delete(id);
    }


}
