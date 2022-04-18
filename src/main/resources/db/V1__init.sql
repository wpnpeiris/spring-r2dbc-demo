CREATE TABLE demo_entry
(
    id bigserial  primary key,
    entry         varchar null,
    created       timestamp not null default now()
);

