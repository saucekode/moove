CREATE TABLE IF NOT EXISTS `users` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name` varchar(50),
    `last_name` varchar(50),
    `username` varchar(30),
    `email` varchar(255),
    `password` varchar(120),
    `roles` varchar(50),

);

CREATE TABLE IF NOT EXISTS `roles` (

    `role_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_roles` varchar(50),

);

INSERT INTO roles(user_roles) VALUES ('customer');
INSERT INTO roles(user_roles) VALUES ('store_owner');
INSERT INTO roles(user_roles) VALUES ('admin');

