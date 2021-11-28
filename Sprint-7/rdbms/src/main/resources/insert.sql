--liquibase formatted sql

--changeset dchernykh:insert

insert into account1(amount, version) values (1000,0);
insert into account1(amount, version) values (0,0);