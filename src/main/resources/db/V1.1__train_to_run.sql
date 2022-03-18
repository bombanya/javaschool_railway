alter table run add train_id integer references train on update cascade on delete restrict;
update run set train_id = (select route.train_id from route where route.route_id = run.route_id);
alter table run alter column train_id set not null;
alter table route drop column train_id;