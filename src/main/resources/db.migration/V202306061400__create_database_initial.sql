-- create database credito_auto;
create table brand
(
    id     serial
        primary key,
    name   varchar(255) not null
        constraint uk_brand_name
            unique,
    status varchar(255) not null
);

create table car_yard
(
    id              serial
        primary key,
    address         varchar(255) not null,
    name            varchar(255) not null,
    nro_sales_point integer      not null,
    phone           varchar(255) not null,
    status          varchar(255) not null
);

create table person
(
    id             serial
        primary key,
    address        varchar(255) not null,
    age            integer      not null,
    identification varchar(255) not null,
    lastname       varchar(255) not null,
    name           varchar(255) not null,
    phone          varchar(255) not null
);

create table customer
(
    birthday               timestamp,
    credit_subject         integer      not null,
    marital_status         varchar(255) not null,
    partner_identification varchar(255),
    partner_name           varchar(255),
    status                 varchar(255) not null,
    id                     bigint       not null
        primary key
        constraint fkr_customer_person_id
            references person
);

create table customer_assignment
(
    id              serial
        primary key,
    assignment_date timestamp    not null,
    status          varchar(255) not null,
    car_yard_id     bigint       not null
        constraint fk_customer_assignment_car_yard_id
            references car_yard,
    customer_id     bigint       not null
        constraint fk_customer_assignment_customer_id
            references customer
);

create table sale_executive
(
    cellphone   varchar(255) not null,
    status      varchar(255) not null,
    id          bigint       not null
        primary key
        constraint fkd8k5e9as98e8oiphums9aqyfp
            references person,
    car_yard_id bigint       not null
        constraint fk_sale_executive_car_yard_id
            references car_yard
);

create table vehicle
(
    id                  serial
        primary key,
    appraisal           numeric(19, 2) not null,
    availability_status varchar(255)   not null,
    engine_capacity     integer        not null,
    model               varchar(255)   not null,
    nro_chassis         varchar(255)   not null,
    plate               varchar(255)   not null
        constraint uk_vehicle_plate
            unique,
    status              varchar(255)   not null,
    type                varchar(255),
    year                integer        not null,
    brand_id            bigint         not null
        constraint fk_vehicle_brand_id
            references brand
);

create table credit_application
(
    id                serial
        primary key,
    elaboration_date  timestamp,
    initial_amount    numeric(19, 2) not null,
    month_max         integer        not null,
    observation       varchar(255),
    quotes            integer        not null,
    status            varchar(255)   not null,
    car_yard_id       bigint
        constraint fk_credit_application_car_yard_id
            references car_yard,
    customer_id       bigint
        constraint fk_credit_application_customer_id
            references customer,
    sale_executive_id bigint         not null
        constraint fk_credit_application_sale_executive_id
            references sale_executive,
    vehicle_id        bigint         not null
        constraint fk_credit_application_vehicle_id
            references vehicle
);




INSERT INTO public.car_yard (address, name, nro_sales_point, phone, status)
VALUES ('Calle Principal', 'Central', 5, '5699491', 'ENABLED');
INSERT INTO public.car_yard (address, name, nro_sales_point, phone, status)
VALUES ('Calle Secundaria 123', 'Showroom', 9, '9499949', 'ENABLED');
INSERT INTO public.car_yard (address, name, nro_sales_point, phone, status)
VALUES ('Calle Centrica', 'Centro Exposicion', 5, '5465465', 'ENABLED');
