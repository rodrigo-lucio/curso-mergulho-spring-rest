create table cliente (
	id bigint not null auto_increment,
    nome varchar(60) not null,
    email varchar(255) not null,
    telefone varchar(20) not null,
    
    primary key (id)
);


insert into cliente (nome, email, telefone) VALUES ('Rodrigo Lucio', 'luciodigo@gmail.com', '49999999999');