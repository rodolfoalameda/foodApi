package com.project.food.domain.serviceImpl;

import com.project.food.domain.exception.NegocioException;
import com.project.food.domain.model.Pedido;
import com.project.food.domain.model.enums.StatusPedido;
import com.project.food.domain.service.FluxoPedidoService;
import com.project.food.domain.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class FluxoPedidoServiceImpl implements FluxoPedidoService {

    @Autowired
    private PedidoService pedidoService;

    @Override
    @Transactional
    public void confirmar(Long pedidoId) {
        Pedido pedido = pedidoService.findOrFail(pedidoId);
        if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
            throw new NegocioException(String.format("Status incorreto %s. Nao pode alterar de %s para %s",
                    pedido.getId(), pedido.getStatus(), StatusPedido.CONFIRMADO));
        }
        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }

    @Override
    @Transactional
    public void entregar(Long pedidoId) {
        Pedido pedido = pedidoService.findOrFail(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
            throw new NegocioException(String.format("Status do pedido nao pode ser alterado de %s para %s",
                    pedido.getStatus(), StatusPedido.ENTREGUE));
        }

        pedido.setStatus(StatusPedido.ENTREGUE);
        pedido.setDataEntrega(OffsetDateTime.now());
    }

    @Override
    @Transactional
    public void cancelar(Long pedidoId) {
        Pedido pedido = pedidoService.findOrFail(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
            throw new NegocioException(String.format("Pedido nao pode ser alterado de %s para %s",
                    pedido.getStatus(), StatusPedido.CANCELADO));
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        pedido.setDataCancelamento(OffsetDateTime.now());
    }
}
