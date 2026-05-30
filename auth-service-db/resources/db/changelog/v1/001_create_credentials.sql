-- liquibase formatted sql

-- changeset Andrew:001_create_credentials
-- comment: Создание таблицы токенов доступа для аптек.

CREATE TABLE credentials
(
    id           UUID        NOT NULL DEFAULT gen_random_uuid(),
    pharmacy_id  UUID        NOT NULL UNIQUE,
    token        VARCHAR(32) NOT NULL,
    active       BOOLEAN     NOT NULL DEFAULT true,
    expired_date DATE        NOT NULL,
    updated_date  DATE        NOT NULL DEFAULT CURRENT_DATE,

    CONSTRAINT pk_credentials PRIMARY KEY (id)
);

COMMENT ON TABLE credentials IS 'Токены доступа для аптек';
COMMENT ON COLUMN credentials.pharmacy_id IS 'ID аптеки (из pharmacy-service)';
COMMENT ON COLUMN credentials.token IS 'Токен доступа';
COMMENT ON COLUMN credentials.expired_date IS 'Дата истечения токена';
COMMENT ON COLUMN credentials.active IS 'Активен ли токен';
COMMENT ON COLUMN credentials.updated_date IS 'Дата последнего обновления';

-- rollback DROP TABLE credentials;