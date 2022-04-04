insert into role (name) SELECT 'ROLE_USER' from role having count(*) < 3
insert into role (name) SELECT 'ROLE_MODERATOR' from role having count(*) < 3
insert into role (name) SELECT 'ROLE_ADMIN' from role having count(*) < 3