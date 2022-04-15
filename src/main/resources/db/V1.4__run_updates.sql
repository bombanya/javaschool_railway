create table run_cancelled_stations (
    run_id integer not null references run on update cascade on delete restrict,
    station_id integer not null references station on update cascade on delete restrict,
    primary key (run_id, station_id)
);

create table run_updates (
    run_id integer not null references run on update cascade on delete restrict,
    station_id integer not null references station on update cascade on delete restrict,
    arrival_delta bigint not null,              -- delta to original stage_arrival in minutes
    departure_delta bigint not null,              -- delta to original stage_departure in minutes
    primary key (run_id, station_id)
);