-- Table: public.licence
ALTER TABLE public.article_numerique
    ADD COLUMN bookseller text;

ALTER TABLE public.article_papier
    ADD COLUMN bookseller text;
