-- Table: public.offre

-- DROP TABLE public.offre;

CREATE TABLE IF NOT EXISTS public.offre
(
    id bigint NOT NULL,
    ean_libraire character varying(13) COLLATE pg_catalog."default",
    reference character varying(255) COLLATE pg_catalog."default",
    duree integer,
    adoption boolean,
    quantite_minimale_achat integer,
    licence character varying(255) COLLATE pg_catalog."default",
    prescripteur boolean,
    libelle character varying(255) COLLATE pg_catalog."default",
    prix_ht numeric(21,2),
    article_numerique_id bigint,
    CONSTRAINT offre_pkey PRIMARY KEY (id),
    CONSTRAINT fk_offre_article_numerique_id FOREIGN KEY (article_numerique_id)
        REFERENCES public.article_numerique (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.offre
    OWNER to usercatalogue;
