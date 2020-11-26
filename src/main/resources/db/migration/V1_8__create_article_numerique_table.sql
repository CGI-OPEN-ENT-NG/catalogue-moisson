-- Table: public.article_numerique

-- DROP TABLE public.article_numerique;

CREATE TABLE IF NOT EXISTS public.article_numerique
(
    id bigint NOT NULL,
    ean character varying(13) COLLATE pg_catalog."default",
    ark character varying(255) COLLATE pg_catalog."default",
    titre character varying(255) COLLATE pg_catalog."default",
    editeur character varying(255) COLLATE pg_catalog."default",
    auteur character varying(255) COLLATE pg_catalog."default",
    collection character varying(255) COLLATE pg_catalog."default",
    distributeur character varying(255) COLLATE pg_catalog."default",
    url_couverture character varying(255) COLLATE pg_catalog."default",
    url_demo character varying(255) COLLATE pg_catalog."default",
    disponibilte character varying(255) COLLATE pg_catalog."default",
    date_disponibilte timestamp without time zone,
    date_parution timestamp without time zone,
    compatible_gar boolean,
    accessible_ent boolean,
    ean_papier character varying(13) COLLATE pg_catalog."default",
    CONSTRAINT article_numerique_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.article_numerique
    OWNER to usercatalogue;
