create table grupo_permissao (grupo_id bigint not null,
 permissao_id bigint not null) engine=MyISAM;

create table restaurante_forma_pagamento (restaurante_id bigint not null,
 forma_pagamento_id bigint not null) engine=MyISAM;

create table tb_cidade (id bigint not null auto_increment,
 nome varchar(255) not null,
 estado_id bigint, primary key (id)) engine=MyISAM;

create table tb_cozinha (id bigint not null auto_increment,
nome varchar(255) not null,
primary key (id)) engine=MyISAM;

create table tb_estado (id bigint not null auto_increment,
nome varchar(255) not null, primary key (id)) engine=MyISAM;

create table tb_forma_pagamento (id bigint not null auto_increment,
descricao varchar(255) not null, primary key (id)) engine=MyISAM;

create table tb_grupo (id bigint not null auto_increment,
nome varchar(255), primary key (id)) engine=MyISAM;

create table tb_permissao (id bigint not null auto_increment,
descricao varchar(255) not null,
 nome varchar(255) not null, primary key (id)) engine=MyISAM;

create table tb_produto (id bigint not null auto_increment,
ativo bit, descricao varchar(255),
nome varchar(255), preco decimal(19,2),
restaurante_id bigint, primary key (id)) engine=MyISAM;

create table tb_restaurante (id bigint not null auto_increment,
data_atualizacao datetime not null,
data_cadastro datetime not null, bairro varchar(255),
 cep varchar(255), complemento varchar(255),
 logradouro varchar(255),
 numero_endereco varchar(255),
 nome varchar(255) not null,
 taxa_frete decimal(19,2) not null,
 id_cozinha bigint not null,
 endereco_cidade_id bigint, primary key (id)) engine=MyISAM;

create table tb_usuario (id bigint not null auto_increment,
 data_cadastro datetime,
 nome varchar(255),
 senha varchar(255),
 primary key (id)) engine=MyISAM;

create table usuario_grupo (usuario_id bigint not null,
 grupo_id bigint not null) engine=MyISAM;


alter table grupo_permissao add constraint FKp0syycx497qf03cd3axwt07h7 foreign key (permissao_id) references tb_permissao (id);
alter table grupo_permissao add constraint FKhskxj0bsnbexqvwfaybqb4qj5 foreign key (grupo_id) references tb_grupo (id);
alter table restaurante_forma_pagamento add constraint FKvmcyfbxboylw9gcoxooanja8 foreign key (forma_pagamento_id) references tb_forma_pagamento (id);
alter table restaurante_forma_pagamento add constraint FKi24qvgjh60batfb1qm9tty4yq foreign key (restaurante_id) references tb_restaurante (id);
alter table tb_cidade add constraint FKlxge3ne91xrep1oe4cvrjldmm foreign key (estado_id) references tb_estado (id);
alter table tb_produto add constraint FK1fh03prrjxetvrl6d4ut5r1r0 foreign key (restaurante_id) references tb_restaurante (id);
alter table tb_restaurante add constraint FKfg14jluyhafigqvd9o3ac1fv6 foreign key (id_cozinha) references tb_cozinha (id);
alter table tb_restaurante add constraint FKp5f7gcswjq064qro5pfjoj49r foreign key (endereco_cidade_id) references tb_cidade (id);
alter table usuario_grupo add constraint FK8gu45sj38s8px00bxgjgl5v4y foreign key (grupo_id) references tb_grupo (id);
alter table usuario_grupo add constraint FKeo4b96b4q0ah4mdgfywrao5nr foreign key (usuario_id) references tb_usuario (id);

