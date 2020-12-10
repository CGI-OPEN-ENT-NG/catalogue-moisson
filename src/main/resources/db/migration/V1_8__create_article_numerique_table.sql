-- Table: public.article_numerique

-- DROP TABLE public.article_numerique;

CREATE TABLE public.article_numerique
(
    id bigint NOT NULL,
    ean character varying(13) COLLATE pg_catalog."default",
    ark character varying(255) COLLATE pg_catalog."default",
    titre character varying(255) COLLATE pg_catalog."default",
    editeur character varying(255) COLLATE pg_catalog."default",
    auteur character varying(1024) COLLATE pg_catalog."default",
    collection character varying(255) COLLATE pg_catalog."default",
    distributeur character varying(255) COLLATE pg_catalog."default",
    url_couverture character varying(255) COLLATE pg_catalog."default",
    url_demo character varying(255) COLLATE pg_catalog."default",
    date_parution timestamp without time zone,
    compatible_gar boolean,
    accessible_ent boolean,
    ean_papier character varying(13) COLLATE pg_catalog."default",
    description character varying(65000) COLLATE pg_catalog."default",
    public_cible character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT article_numerique_pkey PRIMARY KEY (id),
    CONSTRAINT fk_article_numerique_disponibilite_id FOREIGN KEY (id)
        REFERENCES public.disponibilite (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.article_numerique
    OWNER to usercatalogue;
