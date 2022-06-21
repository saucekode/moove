CREATE SEQUENCE IF NOT EXISTS usertablesequence
    INCREMENT 5
    START 1;

CREATE TABLE IF NOT EXISTS users(
    id bigint generated always as identity PRIMARY KEY,
    first_name varchar(50),
    last_name varchar(50),
    username varchar(30),
    email varchar(255),
    password varchar(120),
    user_roles varchar(50)
);

CREATE TABLE IF NOT EXISTS roles (
   role_id int NOT NULL PRIMARY KEY,
   user_roles varchar(50)
);

INSERT INTO roles(role_id, user_roles) VALUES (1, 'USER');
INSERT INTO roles(role_id, user_roles) VALUES (2, 'MODERATOR');
INSERT INTO roles(role_id, user_roles) VALUES (3, 'ADMIN');


