####################################################
# Utilisation de Tomcat au lieu de Spring Boot
# pour être plus proche des tests en production
# avec le contexte /moissoncatalogue
# Environnement de recette déployé avec Garden
####################################################
FROM bitnami/tomcat:9.0.41

WORKDIR /app

## Création des répertoires
RUN mkdir /app/moissoncatalogue

## Création du répertoire pour la configuration
RUN mkdir /app/config
COPY src/main/resources/config/application.yml /app/config
COPY src/main/resources/config/application-prod.yml /app/config

EXPOSE 8080

