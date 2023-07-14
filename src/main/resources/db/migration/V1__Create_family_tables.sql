 CREATE TABLE families (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    CONSTRAINT pk_families PRIMARY KEY (id)
 );

 create table family_logins (
       family_id BIGINT NOT NULL,
       logins VARCHAR(250) NOT NULL,
       primary key (family_id, logins)
   );

 CREATE TABLE family_members (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(250) NOT NULL,
    is_child SMALLINT,
--    family_id BIGINT NOT NULL,
--    age INTEGER,
--    weight DOUBLE PRECISION,
    CONSTRAINT pk_family_members PRIMARY KEY (id)
 );

 CREATE TABLE medicines_to_families (
    families_id BIGINT NOT NULL,
    medicines_id BIGINT NOT NULL,
    primary key (families_id, medicines_id)
  );
