CREATE TABLE family_members (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   dtype VARCHAR(31),
   name VARCHAR(255) NOT NULL,
   is_child BOOLEAN NOT NULL,
   prescribed_medicines_id BIGINT,
   age INT NOT NULL,
   weight FLOAT NOT NULL,
   CONSTRAINT pk_family_members PRIMARY KEY (id)
);

CREATE TABLE medicines (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   name VARCHAR(255) NOT NULL,
   expiration_date TIMESTAMP with time zone NOT NULL,
   period_after_opening INT,
   dosage DOUBLE PRECISION,
   administration_times BLOB,
   CONSTRAINT pk_medicines PRIMARY KEY (id)
);

CREATE TABLE prescribed_medicines (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   CONSTRAINT pk_prescribed_medicines PRIMARY KEY (id)
);

CREATE TABLE users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   login VARCHAR(255),
   password VARCHAR(255),
   CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE family_members ADD CONSTRAINT uc_family_members_name UNIQUE (name);

ALTER TABLE medicines ADD CONSTRAINT uc_medicines_name UNIQUE (name);

ALTER TABLE users ADD CONSTRAINT uc_users_login UNIQUE (login);

ALTER TABLE family_members ADD CONSTRAINT FK_FAMILY_MEMBERS_ON_PRESCRIBEDMEDICINES FOREIGN KEY (prescribed_medicines_id) REFERENCES prescribed_medicines (id);