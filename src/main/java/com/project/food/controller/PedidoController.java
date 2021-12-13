package com.project.food.controller;

import com.project.food.controller.assembler.PedidoDTOAssembler;
import com.project.food.controller.assembler.PedidoInputDisassembler;
import com.project.food.controller.assembler.PedidoResumoDTOAssembler;
import com.project.food.domain.exception.EntidadeNaoEncontradaException;
import com.project.food.domain.exception.NegocioException;
import com.project.food.domain.model.Pedido;
import com.project.food.domain.model.Usuario;
import com.project.food.domain.model.dtos.PedidoDTO;
import com.project.food.domain.model.dtos.PedidoResumoDTO;
import com.project.food.domain.model.input.PedidoInput;
import com.project.food.domain.service.PedidoService;
import com.project.food.repository.PedidoRepository;
import com.project.food.repository.filters.PedidoFilters;
import com.project.food.repository.specs.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoDTOAssembler pedidoDTOAssembler;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoResumoDTOAssembler pedidoResumoDTOAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @GetMapping
    public Page<PedidoResumoDTO> findAll(PedidoFilters filters, Pageable pageable) {
        Page<Pedido> pedidoPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filters), pageable);

        List<PedidoResumoDTO> pedidoDTOS = pedidoResumoDTOAssembler.fromDomainsToDtos(pedidoPage.getContent());

        return new PageImpl<PedidoResumoDTO>(pedidoDTOS, pageable, pedidoPage.getTotalElements());
    }

    @GetMapping("/{id}")
    public PedidoDTO findById(@PathVariable Long id) {
        Pedido pedido = pedidoService.findOrFail(id);
        return pedidoDTOAssembler.fromDomainToDto(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO save(@RequestBody @Valid PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDisassembler.fromModelToDomain(pedidoInput);

            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(5L);

            novoPedido = pedidoService.emitir(novoPedido);
            return pedidoDTOAssembler.fromDomainToDto(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
