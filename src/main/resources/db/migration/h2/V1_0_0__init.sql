-- -----------------------------------------------------
-- Table user_role
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user_role
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    type VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table bank_user
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bank_user
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(45) NOT NULL,
    last_name VARCHAR(45) NOT NULL,
    email VARCHAR(45) NOT NULL,
    password VARCHAR(245) NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_bank_user_user_role
        FOREIGN KEY (role_id)
            REFERENCES user_role (id)
            ON UPDATE CASCADE
);
    CREATE INDEX fk_bank_user_user_role_idx ON bank_user (role_id ASC);

-- -----------------------------------------------------
-- Table bank_account
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bank_account
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    balance DECIMAL NOT NULL,
    currency VARCHAR(45) NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (id),
        CONSTRAINT fk_bank_account_bank_user
            FOREIGN KEY (user_id)
                REFERENCES bank_user (id)
                ON UPDATE CASCADE
);
    CREATE INDEX fk_bank_account_bank_user_idx ON bank_account (user_id ASC);

INSERT INTO user_role (id, type)
VALUES (1L, 'BANK_CLIENT');

INSERT INTO user_role (id, type)
VALUES (2L, 'BANK_EMPLOYEE');
