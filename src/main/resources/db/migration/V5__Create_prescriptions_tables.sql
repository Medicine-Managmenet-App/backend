 CREATE TABLE prescriptions (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    family_member_id BIGINT NOT NULL,
    CONSTRAINT pk_prescriptions PRIMARY KEY (id)
 );

 CREATE TABLE prescribed_medicines (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    medicine_id BIGINT,
    dosage DOUBLE PRECISION,
    number_of_doses INTEGER,
    dosage_interval INTEGER,
    prescription_id BIGINT,
    CONSTRAINT pk_prescribed_medicines PRIMARY KEY (id)
 );

  create table prescribed_medicine_administration_times (
      prescribed_medicine_id BIGINT NOT NULL,
      administration_times TIME NOT NULL,
      primary key (prescribed_medicine_id, administration_times)
  );

ALTER TABLE prescriptions ADD CONSTRAINT FK_PRESCRIPTION_ON_MEMBER FOREIGN KEY (family_member_id) REFERENCES family_members (id);
ALTER TABLE prescribed_medicines ADD CONSTRAINT FK_PRESCRIBED_MEDICINES_ON_MEDICINE FOREIGN KEY (medicine_id) REFERENCES medicines (id);
ALTER TABLE prescribed_medicine_administration_times ADD CONSTRAINT FK_HOURS_MEDICINES_ON_PRESCRIBED_MEDICINES FOREIGN KEY (prescribed_medicine_id) REFERENCES prescribed_medicines (id);



