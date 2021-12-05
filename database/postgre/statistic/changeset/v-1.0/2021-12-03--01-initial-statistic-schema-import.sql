-- liquibase formatted sql
-- changeset 2021-11-02--01-initial-statistic-schema-import
-- changeset izmalkov-rg:2021-11-02--01-initial-statistic-schema-import#0001

CREATE SCHEMA IF NOT EXISTS statistic

--rollback DROP SCHEMA IF EXISTS rdbms