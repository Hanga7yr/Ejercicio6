CREATE DATABASE orm_gestion_proyectos;
use orm_gestion_proyectos;
create table empleado(
	dni char(9) not null,
	nom_emp varchar(32) not null,
	primary key (dni)
);
create table proyecto(
	id_proy integer auto_increment not null,
	nom_proy varchar(32) not null,
	f_inicio date not null,
	f_fin date,
	dni_jefe_proy char(9) not null,
	primary key(id_proy),
	foreign key fk_proy_jefe (dni_jefe_proy) references
	empleado ( dni)
);

create table asig_proyecto(
	dni_emp char(9),
	id_proy integer not null,
	f_inicio date not null,
	f_fin date,
	primary key(dni_emp, id_proy, f_inicio),
	foreign key f_asig_emp (dni_emp) references empleado (dni),
	foreign key f_asig_proy(id_proy) references proyecto(id_proy)
);

create table datos_prof (
	dni char(9) not null,
	categoria char(2) not null,
	sueldo_bruto_anual decimal(8,2),
	primary key (dni),
	foreign key fk_datosprof_empl (dni) references empleado ( dni)
);

CREATE USER 'orm_gestion_proy'@'localhost' IDENTIFIED BY 'orm_gestion_proy';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP,EXECUTE
ON orm_gestion_proyectos.* TO 'orm_gestion_proy'@'localhost';