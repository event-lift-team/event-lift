
create table DRIVERS
(
    ID             BIGINT not null
        primary key,
    EMAIL          VARCHAR(255),
    FIRST_NAME     VARCHAR(255),
    LAST_NAME      VARCHAR(255),
    STAKEHOLDER_ID BIGINT
);

create table EVENTS
(
    ID   BIGINT not null
        primary key,
    NAME VARCHAR(255),
    UUID VARCHAR(255)
);

create table DRIVERS_EVENTS
(
    DRIVER_ID BIGINT not null,
    EVENT_ID  BIGINT not null,
    primary key (DRIVER_ID, EVENT_ID),
    constraint FKC0RDGQPJMWP1G7J1UE48RW3UQ
        foreign key (DRIVER_ID) references DRIVERS,
    constraint FKDFC8L8E81RMINMH8M2YIKHLTM
        foreign key (EVENT_ID) references EVENTS
);

create table HITCHHIKERS
(
    ID             BIGINT not null
        primary key,
    EMAIL          VARCHAR(255),
    FIRST_NAME     VARCHAR(255),
    LAST_NAME      VARCHAR(255),
    STAKEHOLDER_ID BIGINT
);

create table DRIVERS_HITCHHIKERS
(
    HITCHHIKER_ID BIGINT not null,
    DRIVER_ID     BIGINT not null,
    primary key (HITCHHIKER_ID, DRIVER_ID),
    constraint FKBIBDH4PE600QWB8KBXM2MKW7L
        foreign key (HITCHHIKER_ID) references HITCHHIKERS,
    constraint FKKDG0TX0B2GBQ7LNNUTGOCCX5Y
        foreign key (DRIVER_ID) references DRIVERS
);

create table HITCHHIKERS_EVENTS
(
    HITCHHIKER_ID BIGINT not null,
    EVENT_ID      BIGINT not null,
    primary key (HITCHHIKER_ID, EVENT_ID),
    constraint FKJM9PXYT37EP8V91GMF957MGAM
        foreign key (EVENT_ID) references EVENTS,
    constraint FKPJYVPXB4A9UR8FDLNAC3OETHT
        foreign key (HITCHHIKER_ID) references HITCHHIKERS
);

create table ROLES
(
    ID        BIGINT not null
        primary key,
    ROLE_NAME VARCHAR(255)
);

create table STAKEHOLDERS
(
    ID            BIGINT not null
        primary key,
    EMAIL         VARCHAR(255),
    FIRST_NAME    VARCHAR(255),
    LAST_NAME     VARCHAR(255),
    PASSWORD_HASH VARCHAR(255),
    DRIVER_ID     BIGINT,
    HITCHHIKER_ID BIGINT,
    constraint FK95W2AM84PD7SLDHPLRGVOXW0E
        foreign key (HITCHHIKER_ID) references HITCHHIKERS,
    constraint FKJRW99QY97OMG5JFXVOUX3E1KL
        foreign key (DRIVER_ID) references DRIVERS
);

alter table DRIVERS
    add constraint FKJ5AGDT3TH87428KVA8CK0RFVJ
        foreign key (STAKEHOLDER_ID) references STAKEHOLDERS;

alter table HITCHHIKERS
    add constraint FKIJE81E0A6YVN420NOT0COAB5H
        foreign key (STAKEHOLDER_ID) references STAKEHOLDERS;

create table STAKEHOLDERS_ROLES
(
    STAKEHOLDER_ID BIGINT not null,
    ROLE_ID        BIGINT not null,
    primary key (STAKEHOLDER_ID, ROLE_ID),
    constraint FK418U22HRGYEK7KFN8F1U2LHM0
        foreign key (ROLE_ID) references ROLES,
    constraint FKGNJPKUAFSP6FBUWYGG604D7WU
        foreign key (STAKEHOLDER_ID) references STAKEHOLDERS
);

create table TRANSPORT_INFO
(
    ID                     BIGINT not null
        primary key,
    ADDITIONAL_INFORMATION VARCHAR(255),
    NUMBER_OF_SEATS        INTEGER,
    START_LOCATION         VARCHAR(255),
    START_TIME             TIMESTAMP,
    DRIVER_ID              BIGINT,
    EVENT_ID               BIGINT,
    constraint FK4MOBULB13FHL5YQ08UENAWC2D
        foreign key (DRIVER_ID) references DRIVERS,
    constraint FKEWWSYCJVTX15TNBUHDDLO3O7J
        foreign key (EVENT_ID) references EVENTS
);

