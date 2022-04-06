create table user_account (
    user_id serial primary key,
    username text not null unique,
    password text not null
);

create table role (
    role_id serial primary key,
    name text not null unique
);

create table user_roles (
    user_id integer not null references user_account on update cascade on delete restrict,
    role_id integer not null references role on update cascade on delete restrict,
    primary key (user_id, role_id)
);