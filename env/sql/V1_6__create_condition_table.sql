-- Table: public.condition

-- DROP TABLE public.condition;

CREATE TABLE public.condition
(
    id bigint NOT NULL,
    gratuite integer,
    condition_gratuite integer,
    lep_id bigint,
    CONSTRAINT condition_pkey PRIMARY KEY (id),
    CONSTRAINT fk_condition_lep_id FOREIGN KEY (lep_id)
        REFERENCES public.lep (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.condition
    OWNER to usercatalogue;
