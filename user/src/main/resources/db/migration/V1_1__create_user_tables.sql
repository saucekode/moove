CREATE TABLE IF NOT EXISTS users(
    user_id int NOT NULL PRIMARY KEY,
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

INSERT INTO roles(role_id, user_roles) VALUES (1, 'customer');
INSERT INTO roles(role_id, user_roles) VALUES (2, 'store_owner');
INSERT INTO roles(role_id, user_roles) VALUES (3, 'admin');

