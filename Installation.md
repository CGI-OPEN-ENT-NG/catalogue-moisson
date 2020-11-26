# Installation

## Création de la base et des roles

Liquibase est paramétré pour la mise en place des tables et séquences au premier démarrage de l’application,
cependant avant de lancer l’application il faut créer les roles et la base correspondante.
(Les noms de fichiers de scripts sql sont donnés au format Flyway bien que ce dernier n’est activé par défaut,
il sera donc possible de jouer les scripts avec flyway avec les fichiers dont la version est supérieure ou égale à V1--nomdufichier.sql
ces fichiers seront stockés dans le répertoire main/resources/db/migration).
Le fichier V_0**init_user_role_database.sql est le fichier de base quelle que soit la procédure de migration en base de données, son nom est également fourni au format Flyway.
1 - Jouer les scripts dans le fichier V_0**init_user_role_database.sql
