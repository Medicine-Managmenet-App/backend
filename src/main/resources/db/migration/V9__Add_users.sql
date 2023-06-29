insert into users (id, login, password, family_id) values (100, 'login', 'password', 100);
insert into users (id, login, password, family_id) values (101, 'login1', 'password1', 100);
insert into users (id, login, password, family_id) values (102, 'login2', 'password2', 100);
insert into users (id, login, password, family_id) values (103, 'login3', 'password3', 101);

insert into family_logins (family_id, logins) values (100, 'login');
insert into family_logins (family_id, logins) values (100, 'login1');
insert into family_logins (family_id, logins) values (100, 'login2');
insert into family_logins (family_id, logins) values (103, 'login3');

