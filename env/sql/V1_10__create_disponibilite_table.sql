-- Table: public.disponibilite

-- DROP TABLE public.disponibilite;

CREATE TABLE public.disponibilite
(
    id bigint NOT NULL,
    commentaire character varying(255) COLLATE pg_catalog."default",
    date_disponibilite timestamp without time zone,
    commandable boolean,
    valeur character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT disponibilite_pkey PRIMARY KEY (id)
)
    WITH (
        OIDS = FALSE
        )
    TABLESPACE pg_default;

ALTER TABLE public.disponibilite
    OWNER to usercatalogue;
