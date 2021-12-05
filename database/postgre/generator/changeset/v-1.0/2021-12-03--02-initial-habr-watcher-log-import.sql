-- liquibase formatted sql
-- changeset 2021-12-03--02-initial-habr-watcher-log-import
-- changeset izmalkov-rg:2021-12-03--02-initial-habr-watcher-log-import#0001

CREATE TABLE IF NOT EXISTS generator.habr_log_entity
(
    id                      bigserial   constraint habr_log_entity_pk primary key,
    page_id                 varchar(36) not null,
    publication_timestamp   timestamp   not null,
    name                    varchar(36) not null,
    type                    varchar(36) not null
)

--rollback DROP TABLE IF EXISTS generator.habr_log_entity