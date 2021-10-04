# Mise à jour

## Suppression des indices

Au préalable au lancement de nouveau jar (procédure identique à celle d’installation), il faut supprimer l’intégralité des index(indices) dans elasticSearch soit avec kibana

Exemple pour articlenumerique :

    DELETE articlenumerique

Soit avec curl en remplaçant par le bon nom d’hôte et le bon port :

    curl -XDELETE "http://localhost:9200/articlenumerique"

Il faut faire la même chose pour
ArticleNumerique
ArticlePapier
Condition
Discipline
Disponibilite
Lep
Licence
Niveau
Offre
Techno
Tva

## Lancement de l’application

Renommer le fichier
moissoncatalogue-0.0.2-2021-02-10-11-30-00.jar
en moissoncatalogue.jar

Les fichiers de configurations sont inchangés

Lancer application
