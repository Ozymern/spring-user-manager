
insert into users (user_id,age, created_at, email, enabled,hash_id,name,password) values(1,30,'2016-11-16 06:43:19.77','user@gmail.com',true,'HolIxrK9D5xQRHzfmWWODUo3LI0Mk4quPvDqIsGtFxSR','user','$2a$10$rBHolIxrK9D5xQRHzfmWWODUo3LI0Mk4quPvDqIsGtFxSR.lDOGl.');
insert into users (user_id,age, created_at, email, enabled,hash_id,name,password) values(2,31,'2016-11-16 06:43:19.77','admin@gmail.com',true,'HolIxrK9D5xQRHzfmWWODUo3LI0Mk4quPvDqIsGtFxSR','admin','$2a$10$Bmn4yAxNMA43B73lqLj/6ev66Okj2whAV0R6j66e5Yt2mVBCoMHja');

insert into roles (role_id,role) values (1,'USER');
insert into roles (role_id,role) values (2,'ADMIN');

insert into users_roles(users,roles) values(2,1);
insert into users_roles(users,roles) values(1,2);


