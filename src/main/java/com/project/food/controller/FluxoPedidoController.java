package com.project.food.controller;

import com.project.food.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fluxo-pedidos")
public class FluxoPedidoController {

    @Autowired
    private FluxoPedidoService fluxoPedidoService;

    @PutMapping("/{id}/confirmar")
    public void confimarPedido(@PathVariable Long id) {
        fluxoPedidoService.confirmar(id);
    }

    @PutMapping("/{id}/entregar")
    public void entregarPedido(@PathVariable Long id) {
        fluxoPedidoService.entregar(id);
    }

    @PutMapping("/{id}/cancelar")
    public void cancelarPedido(@PathVariable Long id) {
        fluxoPedidoService.cancelar(id);
    }

}
