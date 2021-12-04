-- liquibase formatted sql
-- changeset 2021-12-03--04-initial-generator-log-import
-- changeset izmalkov-rg:2021-12-03--04-initial-generator-log-import#0001

CREATE TABLE IF NOT EXISTS generator.generator_log
(
    id              bigserial   constraint generator_log_pk primary key,
    timestamp       timestamp   not null                                    default now(),
    generator_id    bigint      not null,
    entity_id       bigint      not null,
    executed        boolean     not null                                    default false,

    foreign key (generator_id)  references generator.generator_type (id)
)

--rollback DROP TABLE IF EXISTS generator.generator_log