

create database cashsafe;

create table despesa{

	despesaID serial,
	vencimento date not null,
	descricao varchar(100) not null,
	credor varchar(100),
	categoria int,
	pagamento int not null,
	primary key(despesaID),
	foreign key (categoria) references categoria(categoriaID),
	foreign key (pagamento) references pagamento(pagamentoID)
	
}

create table receita{

	despesaID serial,
	vencimento date not null,
	descricao varchar(100) not null,
	devedor varchar(100),
	categoria int,
	pagamento int not null,
	primary key (despesaID),
	foreign key (categoria) references categoria(nome),
	foreign key (pagamento) references pagamento(pagamentoID)

}

create table categoria{

	categoriaID serial,
	nome varchar(100) not null,
	primary key (categoriaID)
}

create table pagamento{

	pagamentoID serial,
	parcela int not null,
	cartao double not null,
	vista double not null,
	cheque double not null,
	primary key (pagamentoID)

}



