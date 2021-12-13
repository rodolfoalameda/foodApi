create table tb_pedido(
	id bigint not null auto_increment,
    subtotal decimal(10,2) not null,
    taxa_frete decimal(10,2) not null,
    valor_total decimal(10,2) not null,

    restaurante_id bigint not null,
    cliente_id bigint not null,
    forma_pagamento_id bigint not null,

    endereco_cidade_id bigint not null,
    cep varchar(9) not null,
    logradouro varchar(80) not null,
    numero varchar(20) not null,
    complemento varchar(80),
    bairro varchar(80) not null,

    status varchar(20),
    data_criacao datetime not null,
    data_confirmacao datetime not null,
    data_cancelamento datetime not null,
    data_entrega datetime not null,

    primary key(id)
);

	alter table tb_pedido add constraint fk_pedido_restaurante foreign key (restaurante_id) references tb_restaurante(id);
    alter table tb_pedido add constraint fk_pedido_cliente foreign key (cliente_id) references tb_usuario(id);
	alter table tb_pedido add constraint fk_pedido_endereco_cidade foreign key (endereco_cidade_id) references tb_cidade(id);
    alter table tb_pedido add constraint fk_pedido_forma_pagamento foreign key (forma_pagamento_id) references tb_forma_pagamento(id);