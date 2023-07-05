insert into family_members (id, name, is_child, family_id, age, weight) values (100, 'John', 0, 100, 0, 0.0);
insert into family_members (id, name, is_child, family_id, age, weight) values (101, 'Alice', 0, 100, 0, 0.0);
insert into family_members (id, name, is_child, family_id, age, weight) values (102, 'Jenny', 1, 100, 5, 12.5);
insert into family_members (id, name, is_child, family_id, age, weight) values (103, 'George', 0, 101, 0, 0.0);
insert into family_members (id, name, is_child, family_id, age, weight) values (104, 'MemberToDelete', 0, 100, 0, 0.0);

insert into medicines_to_families (families_id, medicines_id) values (100, 101);
insert into medicines_to_families (families_id, medicines_id) values (100, 102);


