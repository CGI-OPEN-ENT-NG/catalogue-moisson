-- Table: public.offre

-- DROP TABLE public.offre;

CREATE TABLE IF NOT EXISTS public.offre
(
  id bigint NOT NULL,
  reference character varying(255),
  duree integer,
  adoption boolean,
  prix double precision,
  disponibilite character varying(255),
  date_de_disponibilite timestamp without time zone,
  quantite_minimale_achat integer,
  condition_livraison character varying(255),
  article_id bigint,
  CONSTRAINT offre_pkey PRIMARY KEY (id),
  CONSTRAINT fk_offre_article_id FOREIGN KEY (article_id)
      REFERENCES public.article (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.offre
  OWNER TO usercatalogue;

