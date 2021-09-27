CREATE SEQUENCE IF NOT EXISTS role_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE sec_authorities
(
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_sec_authorities PRIMARY KEY (name)
);

CREATE TABLE sec_role_authorities
(
    authority_name VARCHAR(50) NOT NULL,
    sec_role_id    BIGINT      NOT NULL,
    CONSTRAINT pk_sec_role_authorities PRIMARY KEY (authority_name, sec_role_id)
);

CREATE TABLE sec_roles
(
    id BIGINT NOT NULL,
    CONSTRAINT pk_sec_roles PRIMARY KEY (id)
);

CREATE TABLE sec_users
(
    id        BIGINT NOT NULL,
    login     VARCHAR(50),
    password  VARCHAR(100),
    firstname VARCHAR(50),
    lastname  VARCHAR(50),
    email     VARCHAR(50),
    activated BOOLEAN,
    CONSTRAINT pk_sec_users PRIMARY KEY (id)
);

CREATE TABLE user_role
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (role_id, user_id)
);

ALTER TABLE sec_users
    ADD CONSTRAINT uc_sec_users_login UNIQUE (login);

ALTER TABLE sec_role_authorities
    ADD CONSTRAINT fk_secrolaut_on_sec_authority FOREIGN KEY (authority_name) REFERENCES sec_authorities (name);

ALTER TABLE sec_role_authorities
    ADD CONSTRAINT fk_secrolaut_on_sec_role FOREIGN KEY (sec_role_id) REFERENCES sec_roles (id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_sec_role FOREIGN KEY (role_id) REFERENCES sec_roles (id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_sec_user FOREIGN KEY (user_id) REFERENCES sec_users (id);

insert into sec_users (id, login, password, firstname, lastname, email, activated)
values (1, 'admin', '$2a$10$uZ2cIt/KkEkwL.AhMbgaDuoLCFPch1bOEgjdo9J4VvzKjjU5DPlqC', 'admin', 'admin', 'admin@mail.com',
        true)
ON CONFLICT DO NOTHING;