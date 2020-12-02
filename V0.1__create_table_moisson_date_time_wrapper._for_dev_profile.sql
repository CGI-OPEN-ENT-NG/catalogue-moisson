-- Table: public.moisson_date_time_wrapper

-- DROP TABLE public.moisson_date_time_wrapper;

CREATE TABLE public.moisson_date_time_wrapper
(
    id bigint NOT NULL,
    instant timestamp without time zone,
    local_date_time timestamp without time zone,
    offset_date_time timestamp without time zone,
    zoned_date_time timestamp without time zone,
    local_time time without time zone,
    offset_time time without time zone,
    local_date date,
    CONSTRAINT "moisson_date_time_wrapperPK" PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.moisson_date_time_wrapper
    OWNER to usercatalogue;
