-- Table: public.discipline

-- DROP TABLE public.discipline;

CREATE TABLE IF NOT EXISTS public.discipline
(
    id bigint NOT NULL,
    libelle character varying(255) COLLATE pg_catalog."default",
    terme character varying(255) COLLATE pg_catalog."default",
    concept character varying(255) COLLATE pg_catalog."default",
    article_numerique_id bigint,
    CONSTRAINT discipline_pkey PRIMARY KEY (id),
    CONSTRAINT fk_discipline_article_numerique_id FOREIGN KEY (article_numerique_id)
        REFERENCES public.article_numerique (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.discipline
    OWNER to usercatalogue;
