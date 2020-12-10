-- Table: public.licence

-- DROP TABLE public.licence;

CREATE TABLE public.licence
(
    id bigint NOT NULL,
    valeur character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT licence_pkey PRIMARY KEY (id)
)
    WITH (
        OIDS = FALSE
        )
    TABLESPACE pg_default;

ALTER TABLE public.licence
    OWNER to usercatalogue;
