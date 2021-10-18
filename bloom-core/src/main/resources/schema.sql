-- drop table `trigger`;
-- drop table command;
-- drop table user;


CREATE TABLE IF NOT EXISTS user
(
    id         char(36)     NOT NULL,
    login      VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    key_phrase VARCHAR(255) NULL,
    roles      VARCHAR(255) NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);



CREATE TABLE IF NOT EXISTS command
(
    id         char(36)     NOT NULL,
    properties VARCHAR(255) NULL,
    name       VARCHAR(255) NULL,
    user_login VARCHAR(255) NULL,
    CONSTRAINT pk_command PRIMARY KEY (id)
);

ALTER TABLE command
    ADD CONSTRAINT IF NOT EXISTS FK_COMMAND_ON_USER_LOGIN FOREIGN KEY (user_login) REFERENCES user (login);



CREATE TABLE IF NOT EXISTS `trigger`
(
    id         char(36)     NOT NULL,
    properties VARCHAR(255) NULL,
    name       VARCHAR(255) NULL,
    user_login VARCHAR(255) NULL,
    CONSTRAINT pk_trigger PRIMARY KEY (id)
);

ALTER TABLE `trigger`
    ADD CONSTRAINT IF NOT EXISTS FK_TRIGGER_ON_USER_LOGIN FOREIGN KEY (user_login) REFERENCES user (login);

