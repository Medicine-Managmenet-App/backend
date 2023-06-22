insert into medicines (id, name, expiration_date, period_after_opening, family_id) values (100, 'med1', '2023-06-15 21:27:17.289601+02', 6, 100);
insert into medicines (id, name, expiration_date, period_after_opening, family_id) values (101, 'med2', '2023-06-15 21:27:17.289601+02', 6, 100);
insert into medicines (id, name, expiration_date, period_after_opening, family_id) values (102, 'med3', '2023-06-15 21:27:17.289601+02', 6, 101);


insert into prescribed_medicines(id, medicine_id, dosage, first_dose, dosage_interval, family_id) values (101, 101, 1.00, '8:00:00', 8,'100');
insert into prescribed_medicines(id, medicine_id, dosage, first_dose, dosage_interval, family_id) values (102, 102, 1.00, '8:00:00', 8, '101');

