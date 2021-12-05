CREATE SCHEMA IF NOT EXISTS generator;

CREATE TABLE IF NOT EXISTS generator.habr_log_entity
(
    id                      bigserial   constraint habr_log_entity_pk primary key,
    page_id                 varchar(36) not null,
    publication_timestamp   timestamp   not null,
    name                    varchar(36) not null,
    type                    varchar(36) not null
);

CREATE TABLE IF NOT EXISTS generator.generator_type
(
    id   bigserial constraint generator_type_pk primary key,
    type varchar(36) unique
);

CREATE TABLE IF NOT EXISTS generator.generator_log
(
    id              bigserial   constraint generator_log_pk primary key,
    timestamp       timestamp   not null                                    default now(),
    generator_id    bigint      not null,
    entity_id       bigint      not null,
    executed        boolean     not null                                    default false,

    foreign key (generator_id)  references generator.generator_type (id)
);

insert into generator.generator_type (type) VALUES ('urn:habr');

