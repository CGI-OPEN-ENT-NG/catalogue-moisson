# Génération des fichiers de contrat d'API

## Génération du Json

Jouer la classe de tests :

    Swagger2MarkupIntTest

Génère le fichier swagger.json en interrogeant l’endpoint /v2/api-docs dans le répertoire target/swagger

Il y a une erreur dans le fichier JSOn généré avec part : trouver la ligne dans le json et supprimer les endpoint avec l’ensemble de ses paramètres.

    Failed to execute goal io.github.swagger2markup:swagger2markup-maven-plugin:1.3.7:convertSwagger2markup (default-cli) on project moissoncatalogue: Failed to execute goal 'convertSwagger2markup': Type of parameter 'part' must not be blank -> [Help 1]

Il faut supprimer le bloc suivant :

    /api/exception-translator-test/missing-servlet-request-part:
        get:
          tags:
            - exception-translator-test-controller
          summary: missingServletRequestPartException
          operationId: missingServletRequestPartExceptionUsingGET
          produces:
            - '*/*'
          parameters:
            - in: formData
              name: part
              description: part
              required: true
              schema:
                type: string
          responses:
            '200':
              description: OK
            '401':
              description: Unauthorized
            '403':
              description: Forbidden
            '404':
              description: Not Found
          deprecated: false

Puis exécuter :

    mvn swagger2markup:convertSwagger2markup

Les fichiers adoc sont générés

    [INFO] Markup document written to: {project-directory}/target/docs/asciidoc/paths.adoc
    [INFO] Markup document written to: {project-directory}/target/docs/asciidoc/definitions.adoc
    [INFO] Markup document written to: {project-directory}/target/docs/asciidoc/security.adoc

Dans le répertoire :

     src/docs/asciidoc/

Créer un fichier

    index.adoc

Avec le contenu suivant

    include::{generated}/overview.adoc[]
    include::{generated}/paths.adoc[]
    include::{generated}/definitions.adoc[]

    {generated}: ${project.basedir}/target/docs/asciidoc
