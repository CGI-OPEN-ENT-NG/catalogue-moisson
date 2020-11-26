-- Table: public.niveau

-- DROP TABLE public.niveau;

CREATE TABLE IF NOT EXISTS public.niveau
(
    id bigint NOT NULL,
    libelle character varying(255) COLLATE pg_catalog."default",
    terme character varying(255) COLLATE pg_catalog."default",
    concept character varying(255) COLLATE pg_catalog."default",
    article_numerique_id bigint,
    CONSTRAINT niveau_pkey PRIMARY KEY (id),
    CONSTRAINT fk_niveau_article_numerique_id FOREIGN KEY (article_numerique_id)
        REFERENCES public.article_numerique (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.niveau
    OWNER to usercatalogue;
