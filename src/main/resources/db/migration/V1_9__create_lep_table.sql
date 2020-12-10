-- Table: public.lep

-- DROP TABLE public.lep;

CREATE TABLE public.lep
(
    id bigint NOT NULL,
    ean character varying(13) COLLATE pg_catalog."default",
    description character varying(65000) COLLATE pg_catalog."default",
    titre character varying(255) COLLATE pg_catalog."default",
    duree character varying(255) COLLATE pg_catalog."default",
    offre_id bigint,
    CONSTRAINT lep_pkey PRIMARY KEY (id),
    CONSTRAINT fk_lep_licence_id FOREIGN KEY (id)
        REFERENCES public.licence (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_lep_offre_id FOREIGN KEY (offre_id)
        REFERENCES public.offre (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.lep
    OWNER to usercatalogue;
