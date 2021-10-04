-- Table: public.article_papier

-- DROP TABLE public.article_papier;

CREATE TABLE public.article_papier
(
    id bigint NOT NULL,
    ean character varying(13) COLLATE pg_catalog."default",
    ark character varying(255) COLLATE pg_catalog."default",
    titre character varying(255) COLLATE pg_catalog."default",
    editeur character varying(255) COLLATE pg_catalog."default",
    auteur character varying(1024) COLLATE pg_catalog."default",
    reference_editeur character varying(255) COLLATE pg_catalog."default",
    collection character varying(255) COLLATE pg_catalog."default",
    distributeur character varying(255) COLLATE pg_catalog."default",
    url_couverture character varying(255) COLLATE pg_catalog."default",
    date_parution timestamp without time zone,
    prix_ht numeric(21,2),
    description character varying(65000) COLLATE pg_catalog."default",
    type character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT article_papier_pkey PRIMARY KEY (id),
    CONSTRAINT fk_article_papier_disponibilite_id FOREIGN KEY (id)
        REFERENCES public.disponibilite (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.article_papier
    OWNER to usercatalogue;
