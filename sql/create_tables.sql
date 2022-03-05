create table country (
    country_id serial primary key,
    name text unique not null
);

create table region (
    region_id serial primary key,
    name text not null,
    country_id integer not null references country on update cascade on delete restrict,
    unique (name, country_id)
);

create table settlement (
    settlement_id serial primary key,
    name text not null,
    region_id integer not null references region on update cascade on delete restrict,
    time_zone text not null,
    unique (name, region_id)
);

create table station (
    station_id serial primary key,
    name text not null,
    settlement_id integer not null references settlement on update cascade on delete restrict,
    unique (name, settlement_id)
);

create table wagon_type (
    wagon_type_id serial primary key,
    name text not null unique,
    toilets integer not null check ( toilets >= 0 )
);

create table wagon (
    wagon_id serial primary key,
    wagon_type_id integer not null references wagon_type on update cascade on delete restrict
);

create table seat (
    seat_id serial primary key,
    wagon_type_id integer not null references wagon_type on update cascade on delete restrict,
    inside_wagon_id integer not null,
    class integer not null,
    power_socket boolean not null,
    lying boolean not null,
    upper boolean not null,
    next_to_table boolean not null,
    unique (wagon_type_id, inside_wagon_id)
);

create table train (
    train_id serial primary key
);

create table train_wagons (
    train_id integer not null references train on update cascade on delete restrict,
    wagon_id integer not null references wagon on update cascade on delete restrict,
    primary key (train_id, wagon_id)
);

create table route (
    route_id serial primary key,
    train_id integer not null references train on update cascade on delete restrict
);

create table route_stations (
    route_id integer not null references route on update cascade on delete restrict,
    station_id integer not null references station on update cascade on delete restrict,
    serial_number_on_the_route integer not null check ( serial_number_on_the_route >= 0 ),
    stage_price integer not null check ( stage_price >= 0 ),
    stage_distance integer not null check ( stage_distance >= 0 ),
    stage_departure bigint not null check ( stage_departure >= 0 ),            --delta in minutes to run start
    stage_arrival bigint not null check ( stage_arrival >= 0 ),                --same
    primary key (route_id, station_id),
    unique (route_id, serial_number_on_the_route)
);

create table run (
    run_id serial primary key,
    route_id integer not null references route on update cascade on delete restrict,
    start_utc timestamp not null,
    finish_utc timestamp not null,
    check ( finish_utc > start_utc )
);

create table passenger (
    passenger_id serial primary key,
    name text not null,
    surname text not null,
    patronymic text,
    birth_date date not null,
    passport_series integer not null, -- totally unsafe I suppose
    passport_number integer not null,
    unique (passport_series, passport_number)
);

create table ticket (
    ticket_id serial primary key,
    run_id integer not null references run on update cascade on delete restrict,
    seat_id integer not null references seat on update cascade on delete restrict,
    wagon_id integer not null references wagon on update cascade on delete restrict,
    passenger_id integer not null references passenger on update cascade on delete restrict,
    start_station_id integer not null references station on update cascade on delete restrict,
    finish_station_id integer not null references station on update cascade on delete restrict,
    price integer not null,
    unique (run_id, seat_id, wagon_id)
);