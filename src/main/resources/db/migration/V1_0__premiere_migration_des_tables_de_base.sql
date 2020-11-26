--
-- Création initial des tables
--

-- DATABASE

-- DROP DATABASE moissoncatalogue;

-- CREATE DATABASE moissoncatalogue WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'fr_FR.UTF-8' LC_CTYPE = 'fr_FR.UTF-8';

-- ALTER DATABASE moissoncatalogue OWNER TO usercatalogue;

\connect moissoncatalogue


-- DROP TABLE databasechangelog

CREATE TABLE IF NOT EXISTS public.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);

ALTER TABLE public.databasechangelog OWNER TO usercatalogue;


-- DROP TABLE databasechangeloglock

CREATE TABLE IF NOT EXISTS public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);

ALTER TABLE public.databasechangeloglock OWNER TO usercatalogue;


-- DROP TABLE jhi_authority

CREATE TABLE IF NOT EXISTS public.jhi_authority (
    name character varying(50) NOT NULL
);

ALTER TABLE public.jhi_authority OWNER TO usercatalogue;


-- DROP TABLE jhi_persistent_audit_event

CREATE TABLE IF NOT EXISTS public.jhi_persistent_audit_event (
    event_id bigint NOT NULL,
    principal character varying(50) NOT NULL,
    event_date timestamp without time zone,
    event_type character varying(255)
);

ALTER TABLE public.jhi_persistent_audit_event OWNER TO usercatalogue;

CREATE TABLE IF NOT EXISTS public.jhi_persistent_audit_evt_data (
    event_id bigint NOT NULL,
    name character varying(150) NOT NULL,
    value character varying(255)
);

ALTER TABLE public.jhi_persistent_audit_evt_data OWNER TO usercatalogue;

-- DROP TABLE jhi_persistent_token

CREATE TABLE IF NOT EXISTS public.jhi_persistent_token (
    series character varying(20) NOT NULL,
    user_id bigint,
    token_value character varying(20) NOT NULL,
    token_date date,
    ip_address character varying(39),
    user_agent character varying(255)
);

ALTER TABLE public.jhi_persistent_token OWNER TO usercatalogue;


-- DROP TABLE jhi_user

CREATE TABLE IF NOT EXISTS public.jhi_user (
    id bigint NOT NULL,
    login character varying(50) NOT NULL,
    password_hash character varying(60) NOT NULL,
    first_name character varying(50),
    last_name character varying(50),
    email character varying(191),
    image_url character varying(256),
    activated boolean NOT NULL,
    lang_key character varying(10),
    activation_key character varying(20),
    reset_key character varying(20),
    created_by character varying(50) NOT NULL,
    created_date timestamp without time zone,
    reset_date timestamp without time zone,
    last_modified_by character varying(50),
    last_modified_date timestamp without time zone
);

ALTER TABLE public.jhi_user OWNER TO usercatalogue;


-- DROP TABLE jhi_user_authority

CREATE TABLE IF NOT EXISTS public.jhi_user_authority (
    user_id bigint NOT NULL,
    authority_name character varying(50) NOT NULL
);

ALTER TABLE public.jhi_user_authority OWNER TO usercatalogue;

-- DROP SEQUENCE sequence_generator
-- Sequence globale à JHipster pour réserver des pools d'id
-- afin qu'hibernate n'ait pas à requêter à chaque incrément

CREATE SEQUENCE public.sequence_generator
    START WITH 1050
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.sequence_generator OWNER TO usercatalogue;


-- INSERT

INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('00000000000000', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', '2020-10-28 17:03:20.653905', 1, 'EXECUTED', '8:b8c27d9dc8db18b5de87cdb8c38a416b', 'createSequence sequenceName=sequence_generator', '', NULL, '3.9.0', NULL, NULL, '3901000619');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('00000000000001', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', '2020-10-28 17:03:20.796492', 2, 'EXECUTED', '8:aad53286647d467b929f919d69edfbbf', 'createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; createTable tableName=jhi_persistent_token; addForeignKeyConstraint baseTableName=jhi_user_a...', '', NULL, '3.9.0', NULL, NULL, '3901000619');

INSERT INTO public.databasechangeloglock (id, locked, lockgranted, lockedby) VALUES (1, false, NULL, NULL);

INSERT INTO public.jhi_authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO public.jhi_authority (name) VALUES ('ROLE_USER');

INSERT INTO public.jhi_user (id, login, password_hash, first_name, last_name, email, image_url, activated, lang_key, activation_key, reset_key, created_by, created_date, reset_date, last_modified_by, last_modified_date) VALUES (1, 'system', '$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG', 'System', 'System', 'system@localhost', '', true, 'fr', NULL, NULL, 'system', NULL, NULL, 'system', NULL);
INSERT INTO public.jhi_user (id, login, password_hash, first_name, last_name, email, image_url, activated, lang_key, activation_key, reset_key, created_by, created_date, reset_date, last_modified_by, last_modified_date) VALUES (2, 'anonymoususer', '$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO', 'Anonymous', 'User', 'anonymous@localhost', '', true, 'fr', NULL, NULL, 'system', NULL, NULL, 'system', NULL);
INSERT INTO public.jhi_user (id, login, password_hash, first_name, last_name, email, image_url, activated, lang_key, activation_key, reset_key, created_by, created_date, reset_date, last_modified_by, last_modified_date) VALUES (3, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator', 'admin@localhost', '', true, 'fr', NULL, NULL, 'system', NULL, NULL, 'system', NULL);
INSERT INTO public.jhi_user (id, login, password_hash, first_name, last_name, email, image_url, activated, lang_key, activation_key, reset_key, created_by, created_date, reset_date, last_modified_by, last_modified_date) VALUES (4, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', '', true, 'fr', NULL, NULL, 'system', NULL, NULL, 'system', NULL);

INSERT INTO public.jhi_user_authority (user_id, authority_name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.jhi_user_authority (user_id, authority_name) VALUES (1, 'ROLE_USER');
INSERT INTO public.jhi_user_authority (user_id, authority_name) VALUES (3, 'ROLE_ADMIN');
INSERT INTO public.jhi_user_authority (user_id, authority_name) VALUES (3, 'ROLE_USER');
INSERT INTO public.jhi_user_authority (user_id, authority_name) VALUES (4, 'ROLE_USER');


-- ALTER TABLE CONSTRAINT  PRIMARY KEY

ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.jhi_authority
    ADD CONSTRAINT jhi_authority_pkey PRIMARY KEY (name);

ALTER TABLE ONLY public.jhi_persistent_audit_event
    ADD CONSTRAINT jhi_persistent_audit_event_pkey PRIMARY KEY (event_id);

ALTER TABLE ONLY public.jhi_persistent_audit_evt_data
    ADD CONSTRAINT jhi_persistent_audit_evt_data_pkey PRIMARY KEY (event_id, name);

ALTER TABLE ONLY public.jhi_persistent_token
    ADD CONSTRAINT jhi_persistent_token_pkey PRIMARY KEY (series);

ALTER TABLE ONLY public.jhi_user_authority
    ADD CONSTRAINT jhi_user_authority_pkey PRIMARY KEY (user_id, authority_name);

ALTER TABLE ONLY public.jhi_user
    ADD CONSTRAINT jhi_user_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.jhi_user
    ADD CONSTRAINT ux_user_email UNIQUE (email);

ALTER TABLE ONLY public.jhi_user
    ADD CONSTRAINT ux_user_login UNIQUE (login);


-- CREATE INDEX

CREATE INDEX idx_persistent_audit_event ON public.jhi_persistent_audit_event USING btree (principal, event_date);

CREATE INDEX idx_persistent_audit_evt_data ON public.jhi_persistent_audit_evt_data USING btree (event_id);


-- ADD CONSTRAINT FOREIGN KEY

ALTER TABLE ONLY public.jhi_user_authority
    ADD CONSTRAINT fk_authority_name FOREIGN KEY (authority_name) REFERENCES public.jhi_authority(name);

ALTER TABLE ONLY public.jhi_persistent_audit_evt_data
    ADD CONSTRAINT fk_evt_pers_audit_evt_data FOREIGN KEY (event_id) REFERENCES public.jhi_persistent_audit_event(event_id);

ALTER TABLE ONLY public.jhi_user_authority
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.jhi_user(id);

ALTER TABLE ONLY public.jhi_persistent_token
    ADD CONSTRAINT fk_user_persistent_token FOREIGN KEY (user_id) REFERENCES public.jhi_user(id);

