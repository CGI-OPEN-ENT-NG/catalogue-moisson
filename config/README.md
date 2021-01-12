Répertoire : config/ qui a la précédence sur src/main/resources/config
Le profile n’est pas positionné dans le fichier config/application.yml

Il est possible de le positionner 
profiles:
  # The commented value for `active` can be replaced with valid Spring profiles to load.
  # Otherwise, it will be filled in by maven when building the JAR file
  # Either way, it can be overridden by `--spring.profiles.active` value passed in the commandline or `-Dspring.profiles.active` set in `JAVA_OPTS`
  # active: prod 
  # active: dev
