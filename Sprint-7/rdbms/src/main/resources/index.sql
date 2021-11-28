--liquibase formatted sql

--changeset dchernykh:index

create index index_account ON account1(id);
