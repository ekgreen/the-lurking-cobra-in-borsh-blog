-- liquibase formatted sql
-- changeset 2021-12-03--03-initial-generator-type-import
-- changeset izmalkov-rg:2021-12-03--03-initial-generator-type-import#0001

CREATE TABLE IF NOT EXISTS generator.generator_type
(
    id   bigserial constraint generator_type_pk primary key,
    type varchar(36) unique
)

--rollback DROP TABLE IF EXISTS generator.generator_type