-- Table: public.article_discipline

-- DROP TABLE public.article_discipline;

CREATE TABLE IF NOT EXISTS public.article_discipline
(
  discipline_id bigint NOT NULL,
  article_id bigint NOT NULL,
  CONSTRAINT article_discipline_pkey PRIMARY KEY (article_id, discipline_id),
  CONSTRAINT fk_article_discipline_discipline_id FOREIGN KEY (discipline_id)
      REFERENCES public.discipline (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_article_discipline_article_id FOREIGN KEY (article_id)
      REFERENCES public.article (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.article_discipline
  OWNER TO usercatalogue;

-- Table: public.article_niveau

-- DROP TABLE public.article_niveau;

CREATE TABLE IF NOT EXISTS public.article_niveau
(
    niveau_id bigint NOT NULL,
    article_id bigint NOT NULL,
    CONSTRAINT article_niveau_pkey PRIMARY KEY (article_id, niveau_id),
    CONSTRAINT fk_article_niveau_niveau_id FOREIGN KEY (niveau_id)
        REFERENCES public.niveau (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fk_article_niveau_article_id FOREIGN KEY (article_id)
        REFERENCES public.article (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
)
    WITH (
        OIDS=FALSE
    );
ALTER TABLE public.article_niveau
    OWNER TO usercatalogue;

-- Table: public.article_collection

-- DROP TABLE public.article_collection;

CREATE TABLE public.article_collection
(
    collection_id bigint NOT NULL,
    article_id bigint NOT NULL,
    CONSTRAINT article_collection_pkey PRIMARY KEY (article_id, collection_id),
    CONSTRAINT fk_article_collection_collection_id FOREIGN KEY (collection_id)
        REFERENCES public.collection (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fk_article_collection_article_id FOREIGN KEY (article_id)
        REFERENCES public.article (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
)
    WITH (
        OIDS=FALSE
    );
ALTER TABLE public.article_collection
    OWNER TO usercatalogue;
