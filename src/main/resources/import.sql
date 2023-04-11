insert into cozinha (nome) values ('Tailandesa');
insert into cozinha (nome) values ('Indiana');
insert into restaurante (nome,taxa_frete,cozinha_id) values ('Oishi Beto',3,2);
insert into restaurante (nome,taxa_frete,cozinha_id) values ('Ursão Burger',2,1);
insert into restaurante (nome,taxa_frete,cozinha_id) values ('Zanata Lanche',0,2);

insert into forma_pagamento(descricao) values('PIX');
insert into forma_pagamento(descricao) values('Cheque');
insert into forma_pagamento(descricao) values('Cartão de Crédito');

insert into permissao(nome,descricao) values('ROLE_CONSULTA','Acesso às funcões de consulta');
insert into permissao(nome) values('ROLE_WRITE');

insert into estado(nome) values('RJ');
insert into estado(nome) values('MG');
insert into estado(nome) values('SP');
insert into estado(nome) values('DF');

insert into cidade(nome,estado_id) values('Queimados',1);
insert into cidade(nome,estado_id) values('Japeri',1);

insert into cidade(nome,estado_id) values('Diadema',2);
insert into cidade(nome,estado_id) values('Belo Horizonte',2);

insert into cidade(nome,estado_id) values('Campinas',3);

insert into cidade(nome,estado_id) values('Ceilandia',4);