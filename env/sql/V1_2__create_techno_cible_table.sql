-- Table: public.techno

-- DROP TABLE public.techno;

CREATE TABLE IF NOT EXISTS public.techno
(
    id bigint NOT NULL,
    technologie character varying(255) COLLATE pg_catalog."default",
    version_reader character varying(255) COLLATE pg_catalog."default",
    available_hors_ent boolean,
    available_via_ent boolean,
    available_via_gar boolean,
    type_licence_gar character varying(255) COLLATE pg_catalog."default" NOT NULL,
    can_use_offline boolean,
    one_clic boolean,
    export_cle_usb boolean,
    deploiement_masse boolean,
    configuration_mini_os character varying(255) COLLATE pg_catalog."default",
    need_flash boolean,
    annotations boolean,
    creation_cours boolean,
    nb_maxi_install character varying(255) COLLATE pg_catalog."default",
    nb_max_simult_connexions character varying(255) COLLATE pg_catalog."default",
    web_adaptatif boolean,
    marque_page boolean,
    capture_image boolean,
    zoom boolean,
    fonctions_recherche boolean,
    corriges_pour_enseignants boolean,
    assignation_taches_eleves boolean,
    partage_contenu_eleves boolean,
    exercices_interactifs boolean,
    exercices_auto_corriges boolean,
    export_reponses_eleves boolean,
    import_document boolean,
    export_document boolean,
    export_scorm boolean,
    personnalisation_user_interface boolean,
    modif_contenu_editorial boolean,
    dispositif_dys boolean,
    article_numerique_id bigint,
    CONSTRAINT techno_pkey PRIMARY KEY (id),
    CONSTRAINT fk_techno_article_numerique_id FOREIGN KEY (article_numerique_id)
        REFERENCES public.article_numerique (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.techno
    OWNER to usercatalogue;
