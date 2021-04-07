-- Table: public.classe

-- DROP TABLE public.classe;

CREATE TABLE IF NOT EXISTS public.classe
(
    id bigint NOT NULL,
    libelle character varying(255) COLLATE pg_catalog."default",
    article_numerique_id bigint,
    CONSTRAINT classe_pkey PRIMARY KEY (id),
    CONSTRAINT fk_classe_article_numerique_id FOREIGN KEY (article_numerique_id)
        REFERENCES public.article_numerique (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.classe
    OWNER to usercatalogue;
