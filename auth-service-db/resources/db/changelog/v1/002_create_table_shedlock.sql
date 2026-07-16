-- liquibase formatted sql

-- changeset Andrew:002_create_table_shedlock
-- comment: Создание таблицы для shedlock.

DROP TABLE IF EXISTS shedlock;

CREATE TABLE shedlock (
    name VARCHAR(64) NOT NULL,
    lock_until TIMESTAMP(3) NOT NULL,
    locked_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    locked_by VARCHAR(255) NOT NULL,
    PRIMARY KEY (name)
);

-- changeset Andrew:003_add_comment_on_table_shedlock
COMMENT ON TABLE shedlock IS 'Информация о блокировки планировщика';
COMMENT ON COLUMN shedlock.name IS 'Наименование блокировки';
COMMENT ON COLUMN shedlock.lock_until IS 'Время снятия блокировки';
COMMENT ON COLUMN shedlock.locked_at IS 'Время начала блокировки';
COMMENT ON COLUMN shedlock.locked_by IS 'Идентификатор ноды инициализировавшей блокировку';

-- rollback DROP TABLE shedlock;