create table users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    login VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL,
    family_id BIGINT NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

