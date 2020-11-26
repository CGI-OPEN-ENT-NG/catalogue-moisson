-- Table: public.discipline

-- DROP TABLE public.discipline;

CREATE TABLE IF NOT EXISTS public.discipline
(
  id bigint NOT NULL,
  name character varying(255),
  concept character varying(255),
  CONSTRAINT discipline_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.discipline
  OWNER TO usercatalogue;
