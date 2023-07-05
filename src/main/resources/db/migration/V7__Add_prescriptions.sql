insert into prescriptions(id, family_member_id) values (100, 100);
insert into prescriptions(id, family_member_id) values (101, 101);
insert into prescriptions(id, family_member_id) values (102, 102);
insert into prescriptions(id, family_member_id) values (103, 103);

insert into prescribed_medicines(id, medicine_id, dosage, number_of_doses, dosage_interval, prescription_id) values (100, 100, 1.00, 3, 24, 100);
insert into prescribed_medicines(id, medicine_id, dosage, number_of_doses, dosage_interval, prescription_id) values (101, 101, 1.00, 3, 24, 100);
insert into prescribed_medicines(id, medicine_id, dosage, number_of_doses, dosage_interval, prescription_id) values (102, 102, 1.00, 3, 24, 101);
insert into prescribed_medicines(id, medicine_id, dosage, number_of_doses, dosage_interval, prescription_id) values (103, 103, 1.00, 3, 24, 103);

insert into prescribed_medicine_administration_times (prescribed_medicine_id, administration_times) values (100, '8:00:00');
insert into prescribed_medicine_administration_times (prescribed_medicine_id, administration_times) values (100, '16:00:00');
insert into prescribed_medicine_administration_times (prescribed_medicine_id, administration_times) values (100, '00:00:00');

insert into prescribed_medicine_administration_times (prescribed_medicine_id, administration_times) values (101, '9:00:00');
insert into prescribed_medicine_administration_times (prescribed_medicine_id, administration_times) values (101, '17:00:00');
insert into prescribed_medicine_administration_times (prescribed_medicine_id, administration_times) values (101, '01:00:00');



