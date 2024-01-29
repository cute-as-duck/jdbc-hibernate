CREATE SEQUENCE seq_tasks START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS public.tasks (
    id integer DEFAULT nextval('seq_tasks'::regclass) NOT NULL PRIMARY KEY,
    name VARCHAR(20),
    owner VARCHAR(20),
    priority INTEGER
);