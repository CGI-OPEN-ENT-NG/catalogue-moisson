-- Table: public.collection

-- DROP TABLE public.collection;

CREATE TABLE IF NOT EXISTS public.collection
(
  id bigint NOT NULL,
  name character varying(255),
  CONSTRAINT collection_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.collection
  OWNER TO usercatalogue;
