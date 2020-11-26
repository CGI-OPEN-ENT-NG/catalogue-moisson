-- Table: public.niveau

-- DROP TABLE public.niveau;

CREATE TABLE IF NOT EXISTS public.niveau
(
  id bigint NOT NULL,
  name character varying(255),
  concept character varying(255),
  CONSTRAINT niveau_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.niveau
  OWNER TO usercatalogue;
