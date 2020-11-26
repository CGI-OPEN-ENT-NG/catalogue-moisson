-- Table: public.eanpapier

-- DROP TABLE public.eanpapier;

CREATE TABLE IF NOT EXISTS public.eanpapier
(
  id bigint NOT NULL,
  ean character varying(13),
  article_id bigint,
  CONSTRAINT eanpapier_pkey PRIMARY KEY (id),
  CONSTRAINT fk_eanpapier_article_id FOREIGN KEY (article_id)
      REFERENCES public.article (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.eanpapier
  OWNER TO usercatalogue;
