insert into prescriptions(id, owner_family_id) values (100, 100);
insert into prescriptions(id, owner_family_id) values (101, 100);
insert into prescriptions(id, owner_family_id) values (102, 100);

insert into prescribed_medicines(id, medicine_id, dosage, number_of_doses, dosage_interval, owner_family_id) values (100, 100, 1.00, 3, 8, 100);
insert into prescribed_medicines(id, medicine_id, dosage, number_of_doses, dosage_interval, owner_family_id) values (101, 101, 1.00, 3, 8, 100);
insert into prescribed_medicines(id, medicine_id, dosage, number_of_doses, dosage_interval, owner_family_id) values (102, 102, 1.00, 3, 8, 101);

insert into prescribed_medicine_administration_times (prescribed_medicine_id, administration_times) values (101, '8:00:00');
insert into prescribed_medicine_administration_times (prescribed_medicine_id, administration_times) values (101, '16:00:00');
insert into prescribed_medicine_administration_times (prescribed_medicine_id, administration_times) values (101, '00:00:00');

insert into prescribed_medicine_administration_times (prescribed_medicine_id, administration_times) values (102, '9:00:00');
insert into prescribed_medicine_administration_times (prescribed_medicine_id, administration_times) values (102, '17:00:00');
insert into prescribed_medicine_administration_times (prescribed_medicine_id, administration_times) values (102, '01:00:00');



