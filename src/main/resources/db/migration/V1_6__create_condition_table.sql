-- Table: public.article_papier

-- DROP TABLE public.article_papier;

CREATE TABLE IF NOT EXISTS public.article_papier
(
    id bigint NOT NULL,
    ean character varying(13) COLLATE pg_catalog."default",
    ark character varying(255) COLLATE pg_catalog."default",
    titre character varying(255) COLLATE pg_catalog."default",
    editeur character varying(255) COLLATE pg_catalog."default",
    auteur character varying(255) COLLATE pg_catalog."default",
    reference_editeur character varying(255) COLLATE pg_catalog."default",
    collection character varying(255) COLLATE pg_catalog."default",
    distributeur character varying(255) COLLATE pg_catalog."default",
    url_couverture character varying(255) COLLATE pg_catalog."default",
    disponibilte character varying(255) COLLATE pg_catalog."default",
    date_disponibilte timestamp without time zone,
    date_parution timestamp without time zone,
    commandable boolean,
    tva numeric(21,2),
    prix_ht numeric(21,2),
    CONSTRAINT article_papier_pkey PRIMARY KEY (id)
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.article_papier
    OWNER to usercatalogue;
