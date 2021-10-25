# Mise à jour

## Suppression des indices

Au préalable au lancement de nouveau jar (procédure identique à celle d’installation), il faut supprimer l’intégralité des index(indices) dans elasticSearch.

Soit avec kibana, Exemple pour articlenumerique :

    DELETE articlenumerique

Soit avec curl en remplaçant par le bon nom d’hôte et le bon port :

    curl -XDELETE "http://localhost:9200/articlenumerique"

Il faut faire la même chose pour ArticlePapier.

## Supprimer les tables

    drop table databasechangeloglock;
    drop table databasechangelog;
    drop table moisson_user_authority;
    drop table moisson_user;
    drop table moisson_authority;
    drop table moisson_persistent_audit_evt_data;
    drop table moisson_persistent_audit_event;
    drop table moisson_date_time_wrapper;
    drop table condition;
    drop table lep;
    drop table techno;
    drop table tva;
    drop table offre;
    drop table niveau;
    drop table discipline;
    drop table article_papier;
    drop table licence;
    drop table classe;
    drop table article_numerique;
    drop table disponibilite;

## Lancement de l’application

Renommer le fichier

    moissoncatalogue-0.0.2-2021-02-10-11-30-00.jar

en :

    moissoncatalogue.jar

Les fichiers de configurations sont inchangés

Lancer application

    java -Dspring.config.location=../config -jar moissoncatalogue.jar
