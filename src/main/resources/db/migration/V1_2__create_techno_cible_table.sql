-- Table: public.public_cible

-- DROP TABLE public.public_cible;

CREATE TABLE IF NOT EXISTS public.public_cible
(
  id bigint NOT NULL,
  label character varying(255),
  type character varying(255),
  CONSTRAINT public_cible_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.public_cible
  OWNER TO usercatalogue;
