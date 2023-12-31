package fr.openent.moisson.service.jackson
    ;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.openent.moisson.domain.*;
import fr.openent.moisson.domain.search.enumeration.*;
import fr.openent.moisson.repository.ArticleNumeriqueRepository;
import fr.openent.moisson.repository.ArticlePapierRepository;
import fr.openent.moisson.repository.search.ArticleNumeriqueSearchRepository;
import fr.openent.moisson.repository.search.ArticlePapierSearchRepository;
import fr.openent.moisson.service.ArticleNumeriqueService;
import fr.openent.moisson.service.ArticlePapierService;
import fr.openent.moisson.service.JsonEntityService;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.util.*;


@SpringBootTest
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JsonFileToEntityObjectMapperTest {

    @Autowired
    protected RestHighLevelClient elasticsearchClient;

    @Autowired
    protected ArticlePapierService articlePapierService;

    @Autowired
    protected ArticleNumeriqueService articleNumeriqueService;

    @Autowired
    protected JsonEntityService jsonEntityService;

    @Autowired
    protected ArticlePapierRepository articlePapierRepository;

    @Autowired
    protected ArticleNumeriqueRepository articleNumeriqueRepository;

    @Autowired
    protected ArticlePapierSearchRepository articlePapierSearchRepository;

    @Autowired
    protected ArticleNumeriqueSearchRepository articleNumeriqueSearchRepository;

    protected static XContentBuilder createDocumentFromArticlePapier(ArticlePapier articlePapier) throws IOException {
        XContentBuilder articlePapierBuilder = XContentFactory.jsonBuilder();
        articlePapierBuilder.startObject();
        articlePapierBuilder.field(ArticlePapierESEnum.EAN.getFieldName(), articlePapier.getEan());
        articlePapierBuilder.field(ArticlePapierESEnum.ARK.getFieldName(), articlePapier.getTitre());
        articlePapierBuilder.field(ArticlePapierESEnum.TITRE.getFieldName(), articlePapier.getArk());
        articlePapierBuilder.field(ArticlePapierESEnum.EDITEUR.getFieldName(), articlePapier.getEditeur());
        articlePapierBuilder.field(ArticlePapierESEnum.AUTEUR.getFieldName(), articlePapier.getAuteur());
        articlePapierBuilder.field(ArticlePapierESEnum.REF_EDITEUR.getFieldName(), articlePapier.getReferenceEditeur());
        articlePapierBuilder.field(ArticlePapierESEnum.COLLECTION.getFieldName(), articlePapier.getCollection());
        articlePapierBuilder.field(ArticlePapierESEnum.DISTRIBUTEUR.getFieldName(), articlePapier.getDistributeur());
        articlePapierBuilder.field(ArticlePapierESEnum.URL_COUVERTURE.getFieldName(), articlePapier.getUrlCouverture());
        articlePapierBuilder.field(ArticlePapierESEnum.DATE_PARUTION.getFieldName(), articlePapier.getDateParution());
        articlePapierBuilder.field(ArticlePapierESEnum.PRIXHT.getFieldName(), articlePapier.getPrixHT());
        articlePapierBuilder.field(ArticlePapierESEnum.DESCRIPTION.getFieldName(), articlePapier.getDescription());
        articlePapierBuilder.field(ArticlePapierESEnum.TYPE.getFieldName(), articlePapier.getType());

        List<Object> Tvas = new ArrayList<>();
        for (Tva tva : articlePapier.getTvas()) {
            Map<String, Object> innerTva = new HashMap<>();
            innerTva.put(TvaESEnum.TAUX.getFieldName(), tva.getTaux());
            innerTva.put(TvaESEnum.POURCENT.getFieldName(), tva.getPourcent());
            Tvas.add(innerTva);
        }
        articlePapierBuilder.field(ArticlePapierESEnum.TVA.getFieldName(), Tvas.toArray());

        List<Object> Disciplines = new ArrayList<>();
        for (Discipline discipline : articlePapier.getDisciplines()) {
            Map<String, Object> innerDiscipline = new HashMap<>();
            innerDiscipline.put(DisciplineESEnum.LIBELLE.getFieldName(), discipline.getLibelle());
            innerDiscipline.put(DisciplineESEnum.TERME.getFieldName(), discipline.getTerme());
            innerDiscipline.put(DisciplineESEnum.CONCEPT.getFieldName(), discipline.getConcept());
            Disciplines.add(innerDiscipline);
        }
        articlePapierBuilder.field(ArticlePapierESEnum.DISCIPLINE.getFieldName(), Disciplines.toArray());

        List<Object> niveaux = new ArrayList<>();
        for (Niveau niveau : articlePapier.getNiveaus()) {
            Map<String, Object> innerNiveau = new HashMap<>();
            innerNiveau.put(NiveauESEnum.LIBELLE.getFieldName(), niveau.getLibelle());
            innerNiveau.put(NiveauESEnum.TERME.getFieldName(), niveau.getTerme());
            innerNiveau.put(NiveauESEnum.CONCEPT.getFieldName(), niveau.getConcept());
            niveaux.add(innerNiveau);
        }
        articlePapierBuilder.field(ArticlePapierESEnum.NIVEAU.getFieldName(), niveaux.toArray());

        List<Object> Disponiblites = new ArrayList<>();
        Disponibilite disponibilite = articlePapier.getDisponibilite();
        Map<String, Object> innerDisponibilite = new HashMap<>();
        innerDisponibilite.put(DisponibiliteESENum.COMMENTAIRE.getFieldName(), disponibilite.getCommentaire());
        innerDisponibilite.put(DisponibiliteESENum.DATE_DISPONIBILITE.getFieldName(), disponibilite.getDateDisponibilite());
        innerDisponibilite.put(DisponibiliteESENum.COMMANDABLE.getFieldName(), disponibilite.isCommandable());
        innerDisponibilite.put(DisponibiliteESENum.VALEUR.getFieldName(), disponibilite.getValeur());
        Disponiblites.add(innerDisponibilite);
        articlePapierBuilder.field(ArticlePapierESEnum.DISPONIBILITE.getFieldName(), Disponiblites.toArray());
        articlePapierBuilder.endObject();
        return articlePapierBuilder;
    }

    protected static XContentBuilder createDocumentFromArticleNumerique(ArticleNumerique articleNumerique) throws IOException {
        XContentBuilder articleNumeriqueBuilder = XContentFactory.jsonBuilder();
        articleNumeriqueBuilder.startObject();
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.EAN.getFieldName(), articleNumerique.getEan());
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.ARK.getFieldName(), articleNumerique.getArk());
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.TITRE.getFieldName(), articleNumerique.getTitre());
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.EDITEUR.getFieldName(), articleNumerique.getEditeur());
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.AUTEUR.getFieldName(), articleNumerique.getAuteur());
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.COLLECTION.getFieldName(), articleNumerique.getCollection());
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.DISTRIBUTEUR.getFieldName(), articleNumerique.getDistributeur());
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.URL_COUVERTURE.getFieldName(), articleNumerique.getUrlCouverture());
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.URL_DEMO.getFieldName(), articleNumerique.getUrlDemo());
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.DATE_PARUTION.getFieldName(), articleNumerique.getDateParution());
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.DESCRIPTION.getFieldName(), articleNumerique.getDescription());
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.COMPATIBLE_GAR.getFieldName(), articleNumerique.isCompatibleGAR());
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.ACCESSIBLE_ENT.getFieldName(), articleNumerique.isAccessibleENT());
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.EAN_PAPIER.getFieldName(), articleNumerique.getEanPapier());
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.PUBLIC_CIBLE.getFieldName(), articleNumerique.getPubliccible());


        List<Object> offres = new ArrayList<>();
        for (Offre offre : articleNumerique.getOffres()) {
            Map<String, Object> innerOffres = new HashMap<>();
            innerOffres.put(OffreESEnum.EAN_LIBRAIRE.getFieldName(), offre.getEanLibraire());
            innerOffres.put(OffreESEnum.QTE_MINI.getFieldName(), offre.getQuantiteMinimaleAchat());
            innerOffres.put(OffreESEnum.PRESCRIPTEUR.getFieldName(), offre.isPrescripteur());
            innerOffres.put(OffreESEnum.LIBELLE.getFieldName(), offre.getLibelle());
            innerOffres.put(OffreESEnum.PRIXHT.getFieldName(), offre.getPrixHT());
            innerOffres.put(OffreESEnum.ADOPTANT.getFieldName(), offre.isAdoptant());
            innerOffres.put(OffreESEnum.DUREE.getFieldName(), offre.getDuree());
            innerOffres.put(OffreESEnum.REF_EDITEUR.getFieldName(), offre.getReferenceEditeur());
            innerOffres.put(OffreESEnum.TYPE.getFieldName(), offre.getType());


            List<Object> innerOffreTvas = new ArrayList<>();
            for (Tva tva : offre.getTvas()) {
                Map<String, Object> innerTva = new HashMap<>();
                innerTva.put(TvaESEnum.TAUX.getFieldName(), tva.getTaux());
                innerTva.put(TvaESEnum.POURCENT.getFieldName(), tva.getPourcent());
                innerOffreTvas.add(innerTva);
            }
            innerOffres.put(OffreESEnum.TVA.getFieldName(), innerOffreTvas);

            List<Object> innerOffreLicences = new ArrayList<>();
            Map<String, Object> innerLicence = new HashMap<>();
            innerLicence.put(LicenceESEnum.VALEUR.getFieldName(), offre.getLicence().getValeur());
            innerOffreLicences.add(innerLicence);
            innerOffres.put(OffreESEnum.LICENCE.getFieldName(), innerOffreLicences);

            List<Object> innerOffreLeps = new ArrayList<>();
            for (Lep lep : offre.getLeps()) {
                Map<String, Object> innerLep = new HashMap<>();
                innerLep.put(LepESEnum.EAN.getFieldName(), lep.getEan());
                innerLep.put(LepESEnum.TITRE.getFieldName(), lep.getTitre());
                innerLep.put(LepESEnum.DESCRIPTION.getFieldName(), lep.getDescription());
                innerLep.put(LepESEnum.DUREE.getFieldName(), lep.getDuree());

                List<Object> innerOffreLepTypeLicences = new ArrayList<>();
                Map<String, Object> innerTypeLicence = new HashMap<>();
                innerTypeLicence.put(LicenceESEnum.VALEUR.getFieldName(), lep.getLicence().getValeur());
                innerOffreLepTypeLicences.add(innerTypeLicence);
                innerLep.put(LepESEnum.TYPELICENCE.getFieldName(), innerOffreLepTypeLicences);

                List<Object> innerOffreLepConditions = new ArrayList<>();
                for (Condition condition : lep.getConditions()) {
                    Map<String, Object> innerConditions = new HashMap<>();
                    innerConditions.put(ConditionESEnum.GRATUITE.getFieldName(), condition.getGratuite());
                    innerConditions.put(ConditionESEnum.CONDITIONGRATUITE.getFieldName(), condition.getConditionGratuite());
                    innerOffreLepConditions.add(innerConditions);
                }
                innerLep.put(LepESEnum.CONDITIONS.getFieldName(), innerOffreLepConditions);
                innerOffreLeps.add(innerLep);
            }
            innerOffres.put(OffreESEnum.LEP.getFieldName(), innerOffreLeps);
            offres.add(innerOffres);
        }
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.OFFRES.getFieldName(), offres.toArray());

        List<Object> technos = new ArrayList<>();
        for (Techno techno : articleNumerique.getTechnos()) {
            Map<String, Object> innerTechno = new HashMap<>();
            innerTechno.put(TechnoESEnum.Technologie.getFieldName(), techno.getTechnologie());
            innerTechno.put(TechnoESEnum.VersionReader.getFieldName(), techno.getVersionReader());
            innerTechno.put(TechnoESEnum.AvailableHorsENT.getFieldName(), techno.isAvailableHorsENT());
            innerTechno.put(TechnoESEnum.AvailableViaENT.getFieldName(), techno.isAvailableViaENT());
            innerTechno.put(TechnoESEnum.AvailableViaGAR.getFieldName(), techno.isAvailableViaGAR());
            innerTechno.put(TechnoESEnum.TypeLicenceGAR.getFieldName(), techno.getTypeLicenceGAR());
            innerTechno.put(TechnoESEnum.CanUseOffline.getFieldName(), techno.isCanUseOffline());
            innerTechno.put(TechnoESEnum.Oneclic.getFieldName(), techno.isOneClic());
            innerTechno.put(TechnoESEnum.ExportCleUSB.getFieldName(), techno.isExportCleUSB());
            innerTechno.put(TechnoESEnum.DeploiementMasse.getFieldName(), techno.isDeploiementMasse());
            innerTechno.put(TechnoESEnum.ConfigurationMiniOS.getFieldName(), techno.getConfigurationMiniOS());
            innerTechno.put(TechnoESEnum.NeedFlash.getFieldName(), techno.isNeedFlash());
            innerTechno.put(TechnoESEnum.Annotations.getFieldName(), techno.isAnnotations());
            innerTechno.put(TechnoESEnum.CreationCours.getFieldName(), techno.isCreationCours());

            // NbMaxiInstall et NbMaxSimultConnexions Commentés à la demande du libraire : sortis du mapping ElasticSeatrch
            // innerTechno.put(TechnoESEnum.NbMaxiInstall.getFieldName(), techno.getNbMaxiInstall());
            // innerTechno.put(TechnoESEnum.NbMaxSimultConnexions.getFieldName(), techno.getNbMaxSimultConnexions());

            innerTechno.put(TechnoESEnum.WebAdaptatif.getFieldName(), techno.isWebAdaptatif());
            innerTechno.put(TechnoESEnum.MarquePage.getFieldName(), techno.isMarquePage());
            innerTechno.put(TechnoESEnum.CaptureImage.getFieldName(), techno.isCaptureImage());
            innerTechno.put(TechnoESEnum.Zoom.getFieldName(), techno.isZoom());
            innerTechno.put(TechnoESEnum.FonctionsRecherche.getFieldName(), techno.isFonctionsRecherche());
            innerTechno.put(TechnoESEnum.CorrigesPourEnseignants.getFieldName(), techno.isCorrigesPourEnseignants());
            innerTechno.put(TechnoESEnum.AssignationTachesEleves.getFieldName(), techno.isAssignationTachesEleves());
            innerTechno.put(TechnoESEnum.PartageContenuEleves.getFieldName(), techno.isPartageContenuEleves());
            innerTechno.put(TechnoESEnum.ExercicesInteractifs.getFieldName(), techno.isExercicesInteractifs());
            innerTechno.put(TechnoESEnum.ExercicesAutoCorriges.getFieldName(), techno.isExercicesAutoCorriges());
            innerTechno.put(TechnoESEnum.ExportReponsesEleves.getFieldName(), techno.isExportReponsesEleves());
            innerTechno.put(TechnoESEnum.ImportDocument.getFieldName(), techno.isImportDocument());
            innerTechno.put(TechnoESEnum.ExportDocument.getFieldName(), techno.isExportDocument());
            innerTechno.put(TechnoESEnum.ExportSCORM.getFieldName(), techno.isExportSCORM());
            innerTechno.put(TechnoESEnum.PersonnalisationUserInterface.getFieldName(), techno.isPersonnalisationUserInterface());
            innerTechno.put(TechnoESEnum.ModifContenuEditorial.getFieldName(), techno.isModifContenuEditorial());
            innerTechno.put(TechnoESEnum.DispositifDYS.getFieldName(), techno.isDispositifDYS());

            technos.add(innerTechno);
        }
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.TECHNO.getFieldName(), technos.toArray());

        List<Object> disciplines = new ArrayList<>();
        for (Discipline discipline : articleNumerique.getDisciplines()) {
            Map<String, Object> innerDiscipline = new HashMap<>();
            innerDiscipline.put(DisciplineESEnum.LIBELLE.getFieldName(), discipline.getLibelle());
            innerDiscipline.put(DisciplineESEnum.TERME.getFieldName(), discipline.getTerme());
            innerDiscipline.put(DisciplineESEnum.CONCEPT.getFieldName(), discipline.getConcept());
            disciplines.add(innerDiscipline);
        }
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.DISCIPLINE.getFieldName(), disciplines.toArray());

        List<Object> niveaux = new ArrayList<>();
        for (Niveau niveau : articleNumerique.getNiveaus()) {
            Map<String, Object> innerNiveau = new HashMap<>();
            innerNiveau.put(NiveauESEnum.LIBELLE.getFieldName(), niveau.getLibelle());
            innerNiveau.put(NiveauESEnum.TERME.getFieldName(), niveau.getTerme());
            innerNiveau.put(NiveauESEnum.CONCEPT.getFieldName(), niveau.getConcept());
            niveaux.add(innerNiveau);
        }
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.NIVEAU.getFieldName(), niveaux.toArray());

        List<Object> classes = new ArrayList<>();
        for (Classe classe : articleNumerique.getClasses()) {
            Map<String, Object> innerClasse = new HashMap<>();
            innerClasse.put(NiveauESEnum.LIBELLE.getFieldName(), classe.getLibelle());
            classes.add(innerClasse);
        }
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.CLASSE.getFieldName(), classes.toArray());

        List<Object> Disponibilites = new ArrayList<>();
        Disponibilite disponibilite = articleNumerique.getDisponibilite();
        Map<String, Object> innerDisponibilite = new HashMap<>();
        innerDisponibilite.put(DisponibiliteESENum.COMMENTAIRE.getFieldName(), disponibilite.getCommentaire());
        innerDisponibilite.put(DisponibiliteESENum.DATE_DISPONIBILITE.getFieldName(), disponibilite.getDateDisponibilite());
        innerDisponibilite.put(DisponibiliteESENum.COMMANDABLE.getFieldName(), disponibilite.isCommandable());
        innerDisponibilite.put(DisponibiliteESENum.VALEUR.getFieldName(), disponibilite.getValeur());
        Disponibilites.add(innerDisponibilite);
        articleNumeriqueBuilder.field(ArticleNumeriqueESEnum.DISPONIBILITE.getFieldName(), Disponibilites.toArray());
        articleNumeriqueBuilder.endObject();
        return articleNumeriqueBuilder;
    }

    /**
     * Correspondance entre un ArticlePapier et un document Elasticsearch à indexer
     * @throws IOException
     */
    @Test
    public void jacksonArticlePapierTest() throws IOException {
        // Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        //var articlePapiers = objectMapper.readValue(new File("src/test/resources/json/articles_papiers.json"), new TypeReference<List<ArticlePapier>>() {
        var articlePapiers = objectMapper.readValue(jsonEntityService.getJsonFromUrl("https://www.lde.fr/4dlink1/4DCGI/IDF/json_cat_pap.json"), new TypeReference<List<ArticlePapier>>() {
        });
        // La relation est bidirectionnelle, il est logique que chaque côté de la relation soit mappé à l'autre,
        // il faut avoir une référence de chaque côté de l'autre côté
        articlePapiers.forEach(articlePapier ->
            {
                Optional<ArticlePapier> existArticlePapier = articlePapierRepository.findByEan(articlePapier.getEan());
                existArticlePapier.ifPresent(presentPapier -> {
                    System.out.println(presentPapier.getPrixTTC());
                    articlePapierRepository.deleteById(presentPapier.getId());
                    try {
                        deleteArticlePapier(presentPapier);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                articlePapier.getTvas().forEach(articlePapier::addTva);
                articlePapier.getNiveaus().forEach(articlePapier::addNiveau);
                articlePapier.getDisciplines().forEach(articlePapier::addDiscipline);
                articlePapierRepository.save(articlePapier);
                try {
                    updateArticlePapier(articlePapier);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        );
        System.out.println("FIN");
    }

    /**
     * Correspondance entre un ArticleNumerique et un document Elasticsearch à indexer
     * @throws IOException
     */
    @Test
    public void jacksonArticleNumeriqueTest() throws IOException {
        // Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        var articleNumeriques = objectMapper.readValue(jsonEntityService.getJsonFromUrl("https://www.lde.fr/4dlink1/4DCGI/IDF/json_cat_num.json"), new TypeReference<List<ArticleNumerique>>() {});
//       var articleNumeriques = objectMapper.readValue(new File("src/test/resources/json/articles_numeriques.json"),
//          new TypeReference<List<ArticleNumerique>>() {
//           });
        articleNumeriques.forEach(articleNumerique ->
            {
                Optional<ArticleNumerique> existArticleNumerique = articleNumeriqueRepository.findByEan(articleNumerique.getEan());
                existArticleNumerique.ifPresent(presentNumerique -> {
                    articleNumeriqueService.delete(presentNumerique.getId());
                    try {
                        deleteArticleNumerique(presentNumerique);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                for (Offre offre : articleNumerique.getOffres()) {
                    for (Lep lep : offre.getLeps()) {
                        lep.getConditions().forEach(lep::addCondition);
                        offre.addLep(lep);
                    }
                    offre.getTvas().forEach(offre::addTva);
                    articleNumerique.addOffre(offre);
                }
                articleNumerique.getNiveaus().forEach(articleNumerique::addNiveau);
                articleNumerique.getDisciplines().forEach(articleNumerique::addDiscipline);
                articleNumerique.getTechnos().forEach(articleNumerique::addTechno);
                articleNumeriqueService.save(articleNumerique);
                try {
                    updateArticleNumerique(articleNumerique);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        );
    }

    public UpdateResponse updateArticlePapier(ArticlePapier articlePapier) throws IOException {

        // Builder
        XContentBuilder builder = createDocumentFromArticlePapier(articlePapier);
        // Request
        UpdateRequest request = new UpdateRequest("articlepapier", articlePapier.getEan()).doc(builder);
        // Create or update
        request.upsert(builder);

        // Execute and return
        return elasticsearchClient.update(request, RequestOptions.DEFAULT);
    }

    public UpdateResponse updateArticleNumerique(ArticleNumerique articleNumerique) throws IOException {

        // Builder
        XContentBuilder builder = createDocumentFromArticleNumerique(articleNumerique);

        // Request
        var request = new UpdateRequest("articlenumerique", articleNumerique.getEan()).doc(builder);

        // Create or update
        request.upsert(builder);
        // Execute and return
        return elasticsearchClient.update(request, RequestOptions.DEFAULT);
    }

    public void indexTitrePublie(ArticlePapier articlePapier) throws ElasticsearchException, IOException {

        try {
            updateArticlePapier(articlePapier);
        } catch (ElasticsearchException | IOException e) {
            throw e;
        }

        // Et optimisation
        try {
            refreshArticlePapier();
        } catch (ElasticsearchException e) {
            throw new ElasticsearchException(e.getMessage());
        }
    }

    public DeleteResponse deleteArticlePapier(ArticlePapier articlePapier) throws ElasticsearchException, IOException {
        DeleteRequest deleteRequest = new DeleteRequest("articlepapier", articlePapier.getEan().toString());
        return elasticsearchClient.delete(deleteRequest, RequestOptions.DEFAULT);
    }

    public DeleteResponse deleteArticleNumerique(ArticleNumerique articleNumerique) throws ElasticsearchException, IOException {
        DeleteRequest deleteRequest = new DeleteRequest("articlenumerique", articleNumerique.getEan().toString());
        return elasticsearchClient.delete(deleteRequest, RequestOptions.DEFAULT);
    }

    public RefreshResponse refreshArticlePapier() throws ElasticsearchException, IOException {
        return refresh("articlepapier");
    }

    public RefreshResponse refreshArticleNumerique() throws ElasticsearchException, IOException {
        return refresh("articlenumerique");
    }

    public RefreshResponse refresh(String indexName) throws ElasticsearchException, IOException {
        RefreshRequest request = new RefreshRequest(indexName);
        return elasticsearchClient.indices().refresh(request, RequestOptions.DEFAULT);
    }

}
