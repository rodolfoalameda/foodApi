create table tb_item_pedido(
	id bigint not null auto_increment,
    quantidade bigint not null,
    preco_unitario decimal(10,2) not null,
    preco_total decimal(10,2) not null,
    observacao varchar(255),

    pedido_id bigint not null,
    produto_id bigint not null,

    primary key(id),

    constraint fk_pedido foreign key(pedido_id) references tb_pedido(id),
    constraint fk_produto foreign key(produto_id) references tb_produto(id)
);