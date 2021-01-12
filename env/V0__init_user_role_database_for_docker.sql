-- Mise à jour du role catalogue déjà créé lors de la création de l'instance de docker : usercatalogue

-- Database: moissoncatalogue

-- DROP DATABASE moissoncatalogue;

CREATE DATABASE moissoncatalogue
    WITH OWNER = usercatalogue
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    LC_COLLATE = 'fr_FR.UTF-8'
    LC_CTYPE = 'fr_FR.UTF-8'
    CONNECTION LIMIT = -1;

-- Grant pour public et usercatalogue
-- En postgres GRANT ALL et GRANT ALL PRIVILEGES sont équivalents
GRANT CONNECT, TEMPORARY ON DATABASE moissoncatalogue TO public;
GRANT ALL ON DATABASE moissoncatalogue TO usercatalogue;

