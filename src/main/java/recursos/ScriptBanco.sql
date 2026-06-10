drop database if exists treino; 
create database treino;

use treino;

create table users(
	id int auto_increment primary key,
	name varchar(100),
	email varchar(100) unique,
	user_password varchar(100),
	idade int,
	weight double,
	height double,
	level varchar(20),
	
	constraint level_values check(level in ('Iniciante', 'Intermediário', 'Avançado'))
);

insert into users values(1,'Luiz Guilherme','luiz@email.com','123456',20,70.0,1.75,'Intermediário');

create table exercises(
	id int auto_increment primary key,
	name varchar(100),
	type varchar(100),
	muscle_group char(10),
	description text,
	
	constraint muscle_grp_values check(muscle_group in ('Peito', 'Costas','Pernas', 'Ombros', 'Core')),
	constraint type_values check(type in ('Repetição', 'Isométrico','Cardio'))
);

create table training(
	id int auto_increment primary key,
	name varchar(100),
	day_week char(20), 	
	user_id int,	
	
	constraint dayWk_values check(day_week in ('Segunda_feira', 'Terça-feira','Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado', 'Domingo')),
	constraint training_user_fk foreign key(user_id) references users(id)
);

create table training_exercise(
	training_id int,
	exercise_id int,
	
	constraint training_trexcs_fk foreign key(training_id) references training(id),
	constraint exercise_trexcs_fk foreign key(exercise_id) references exercises(id)
);

create table history(
	id int auto_increment primary key,
	user_id int,
	training_date date,
	observetions text,
	
	constraint history_user_fk foreign key (user_id) references users(id)
);

create table training_session(
	id int auto_increment primary key,
	training_id int,
	training_date date,
	duracao_minutos int,
	
	constraint training_session_fk foreign key(training_id) references training(id)
);

create table exercise_record(
	id int auto_increment primary key,
	training_session_id int,
	exercise_id int,
	resultado varchar(100),
	
	constraint record_session_fk foreign key(training_session_id) references training_session(id),
	constraint record_exercise_fk foreign key(exercise_id) references exercises(id)

);


