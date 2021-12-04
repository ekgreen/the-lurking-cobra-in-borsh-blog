-- liquibase formatted sql
-- changeset 2021-11-02--01-initial-generator-schema-import
-- changeset izmalkov-rg:2021-11-02--01-initial-generator-schema-import#0001

CREATE SCHEMA IF NOT EXISTS generator

--rollback DROP SCHEMA IF EXISTS rdbms