drop table if exists DJ;
create table DJ(ID bigint primary key not null,
                NAME varchar(255),
                GENRE varchar(255)
               );

drop table if exists PERFORMANCE;
create table PERFORMANCE(ID bigint primary key not null,
                NAME varchar(255),
                GENRE varchar(255),
                DJ_ID bigint, foreign key(DJ_ID) references DJ(ID)
                on delete cascade
);

insert into DJ values (1, 'KoRn', 'metal');
insert into DJ values (2, 'Macklemore', 'pop');

insert into PERFORMANCE values (1, 'Fuizenfest', 'metal', 1);
insert into PERFORMANCE values (2, 'Glastonbury', 'pop', 2);


