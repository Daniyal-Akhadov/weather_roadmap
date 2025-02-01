--changeset daniyal:1

create table public.Users
(
    id       serial primary key,
    login    varchar(128) not null unique,
    password varchar(128) not null
);

create table public.Locations
(
    id        serial primary key,
    name      varchar(128)              not null,
    user_id   int references Users (id) not null,
    latitude  decimal                   not null,
    longitude decimal                   not null
);

create table public.Sessions
(
    id        varchar primary key,
    user_id   int references Users (id) not null,
    expiresAt timestamp                 not null
);