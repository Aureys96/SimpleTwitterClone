SET SQL_SAFE_UPDATES = 0;
delete from user_role;
delete from usr;

insert into usr(id, username, password, active) values
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', true),
(2, 'mike', '21232f297a57a5a743894a0e4a801fc3', true);

insert into user_role(user_id, roles) values
(1, 'ADMIN'), (1, 'USER'),
(2, 'USER');