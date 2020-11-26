-- Table: public.tva

-- DROP TABLE public.tva;

CREATE TABLE IF NOT EXISTS public.tva
(
  id bigint NOT NULL,
  label character varying(255),
  taux double precision,
  CONSTRAINT tva_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tva
  OWNER TO usercatalogue;
