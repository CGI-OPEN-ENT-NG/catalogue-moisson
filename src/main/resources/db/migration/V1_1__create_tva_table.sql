-- Table: public.tva

-- DROP TABLE public.tva;

CREATE TABLE IF NOT EXISTS public.tva
(
    id bigint NOT NULL,
    taux numeric(21,2),
    pourcent numeric(21,2),
    offre_id bigint,
    CONSTRAINT tva_pkey PRIMARY KEY (id),
    CONSTRAINT fk_tva_offre_id FOREIGN KEY (offre_id)
        REFERENCES public.offre (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.tva
    OWNER to usercatalogue;
