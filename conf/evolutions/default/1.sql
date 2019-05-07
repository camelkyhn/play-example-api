# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table role (
  id                            bigserial not null,
  updated_user_id               bigint,
  status                        integer,
  name                          varchar(255),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint ck_role_status check ( status in (0,1,2)),
  constraint pk_role primary key (id)
);

create table usr (
  id                            bigserial not null,
  updated_user_id               bigint,
  status                        integer,
  name                          varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint ck_usr_status check ( status in (0,1,2)),
  constraint pk_usr primary key (id)
);

create table user_role (
  id                            bigserial not null,
  updated_user_id               bigint,
  status                        integer,
  user_id                       bigint,
  role_id                       bigint,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint ck_user_role_status check ( status in (0,1,2)),
  constraint pk_user_role primary key (id)
);

create index ix_role_updated_user_id on role (updated_user_id);
alter table role add constraint fk_role_updated_user_id foreign key (updated_user_id) references usr (id) on delete restrict on update restrict;

create index ix_usr_updated_user_id on usr (updated_user_id);
alter table usr add constraint fk_usr_updated_user_id foreign key (updated_user_id) references usr (id) on delete restrict on update restrict;

create index ix_user_role_updated_user_id on user_role (updated_user_id);
alter table user_role add constraint fk_user_role_updated_user_id foreign key (updated_user_id) references usr (id) on delete restrict on update restrict;

create index ix_user_role_user_id on user_role (user_id);
alter table user_role add constraint fk_user_role_user_id foreign key (user_id) references usr (id) on delete restrict on update restrict;

create index ix_user_role_role_id on user_role (role_id);
alter table user_role add constraint fk_user_role_role_id foreign key (role_id) references role (id) on delete restrict on update restrict;


# --- !Downs

alter table if exists role drop constraint if exists fk_role_updated_user_id;
drop index if exists ix_role_updated_user_id;

alter table if exists usr drop constraint if exists fk_usr_updated_user_id;
drop index if exists ix_usr_updated_user_id;

alter table if exists user_role drop constraint if exists fk_user_role_updated_user_id;
drop index if exists ix_user_role_updated_user_id;

alter table if exists user_role drop constraint if exists fk_user_role_user_id;
drop index if exists ix_user_role_user_id;

alter table if exists user_role drop constraint if exists fk_user_role_role_id;
drop index if exists ix_user_role_role_id;

drop table if exists role cascade;

drop table if exists usr cascade;

drop table if exists user_role cascade;

