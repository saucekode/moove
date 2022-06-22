CREATE SEQUENCE IF NOT EXISTS usertablesequence
    INCREMENT 5
    START 1;

CREATE TABLE IF NOT EXISTS users(
    id bigint generated always as identity PRIMARY KEY,
    first_name varchar(50),
    last_name varchar(50),
    username varchar(30),
    email varchar(255),
    password varchar(120)
);

CREATE TABLE IF NOT EXISTS roles(
    id bigint generated always as identity PRIMARY KEY,
    name varchar(50)
);

CREATE TABLE IF NOT EXISTS user_roles (
   user_id BIGINT NOT NULL,
   role_id BIGINT NOT NULL
);

INSERT INTO roles(name) VALUES ('USER');
INSERT INTO roles(name) VALUES ('MODERATOR');
INSERT INTO roles(name) VALUES ('ADMIN');


