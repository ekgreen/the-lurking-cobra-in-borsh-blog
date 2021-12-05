-- liquibase formatted sql
-- changeset 2021-12-03--02-initial-rating-scheduling-task-import
-- changeset izmalkov-rg:2021-12-03--02-initial-rating-scheduling-task-import#0001

CREATE TABLE IF NOT EXISTS statistic.rating_scheduling_task
(
    id                  bigserial   constraint rating_scheduling_task_pk primary key,
    publication_id      varchar(36) not null,
    launch_timestamp    timestamp   not null,
    grow_time           bigint      not null,
    grow_rate           decimal     not null,
    status              varchar(10) not null
)

--rollback DROP TABLE IF EXISTS statistic.rating_scheduling_task