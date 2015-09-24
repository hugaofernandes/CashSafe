#drop database cashsafe;
#create database cashsafe;

create table categoria(

	categoriaId mediumint auto_increment not null,
	nome varchar(20) not null,
	primary key (categoriaId)
);

create table despesa(

	despesaId mediumint auto_increment not null,
	dataVencimento date not null,
	descricao varchar(20) not null,
	categoria mediumint,
	formaPagamento enum("dinheiro", "cartao") not null,
	pago enum("sim", "nao") not null,
    	despesaFixa enum("sim", "nao"),
    	repetirDespesa date,
	primary key(despesaId),
	foreign key (categoria) references categoria(categoriaId)	
);

create table receita(

	receitaId mediumint auto_increment not null,
	dataRecebimento date not null,
	descricao varchar(20) not null,
	categoria mediumint,
    	recebido enum("sim", "nao") not null,
    	receitaFixa enum("sim", "nao"),
    	repetirReceita date,
	primary key (receitaId),
	foreign key (categoria) references categoria(categoriaId)

);

