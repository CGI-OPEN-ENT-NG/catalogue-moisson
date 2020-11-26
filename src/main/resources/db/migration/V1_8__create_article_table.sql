-- Table: public.article

-- DROP TABLE public.article;

CREATE TABLE IF NOT EXISTS public.article
(
  id bigint NOT NULL,
  ean character varying(13),
  titre character varying(255),
  editeur character varying(255),
  reference_editeur character varying(255),
  distributeur character varying(255),
  url_image character varying(255),
  configuration_minimale character varying(255),
  description character varying(255),
  compatible_gar boolean,
  disponibilite boolean,
  date_de_disponibilite timestamp without time zone,
  url_demo character varying(255),
  date_parution timestamp without time zone,
  accessible_ent boolean,
  commandable boolean,
  CONSTRAINT article_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.article
  OWNER TO usercatalogue;
