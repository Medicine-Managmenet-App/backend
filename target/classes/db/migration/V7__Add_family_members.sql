insert into members (id, name, is_child, family_id, prescription_id) values (100, 'John', false, 100, 100);
insert into members (id, name, is_child, family_id, prescription_id) values (101, 'Alice', false, 100, 101);
insert into members (id, name, is_child, family_id, prescription_id) values (102, 'Jenny', true, 100, 102);
insert into members (id, name, is_child, family_id, prescription_id) values (103, 'George', false, 101, 103);

insert into family_members_child (age, weight, id) values ( 5, 12.5, 102);

insert into medicines_to_families (families_id, medicines_id) values (100, 101);
insert into medicines_to_families (families_id, medicines_id) values (100, 102);


