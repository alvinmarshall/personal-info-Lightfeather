CREATE TABLE supervisors
(
    id                    BIGINT NOT NULL,
    phone                 VARCHAR(50),
    jurisdiction          VARCHAR(100),
    identification_number VARCHAR(100),
    first_name            VARCHAR(100),
    last_name             VARCHAR(100),
    CONSTRAINT pk_supervisors PRIMARY KEY (id)
);
