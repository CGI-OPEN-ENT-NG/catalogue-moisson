-- Table: public.offre

-- DROP TABLE public.offre;

CREATE TABLE IF NOT EXISTS public.offre
(
    id bigint NOT NULL,
    ean_libraire character varying(13) COLLATE pg_catalog."default",
    quantite_minimale_achat integer,
    prescripteur boolean,
    libelle character varying(255) COLLATE pg_catalog."default",
    prix_ht numeric(21,2),
    adoptant boolean,
    duree character varying(255) COLLATE pg_catalog."default",
    reference_editeur character varying(255) COLLATE pg_catalog."default",
    article_numerique_id bigint,
    CONSTRAINT offre_pkey PRIMARY KEY (id),
    CONSTRAINT fk_offre_article_numerique_id FOREIGN KEY (article_numerique_id)
        REFERENCES public.article_numerique (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_offre_licence_id FOREIGN KEY (id)
        REFERENCES public.licence (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.offre
    OWNER to usercatalogue;
