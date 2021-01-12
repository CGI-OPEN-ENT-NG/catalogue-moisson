package fr.openent.moisson.service.jackson
    ;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.openent.moisson.domain.*;
import fr.openent.moisson.domain.search.ESField;
import fr.openent.moisson.domain.search.enumeration.ArticlePapierESEnum;
import fr.openent.moisson.domain.search.enumeration.TvaESEnum;
import fr.openent.moisson.repository.ArticleNumeriqueRepository;
import fr.openent.moisson.repository.ArticlePapierRepository;
import fr.openent.moisson.repository.search.ArticleNumeriqueSearchRepository;
import fr.openent.moisson.repository.search.ArticlePapierSearchRepository;
import fr.openent.moisson.service.ArticleNumeriqueService;
import fr.openent.moisson.service.ArticlePapierService;
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
    protected ArticlePapierRepository articlePapierRepository;

    @Autowired
    protected ArticleNumeriqueRepository articleNumeriqueRepository;

    @Autowired
    protected ArticlePapierSearchRepository articlePapierSearchRepository;

    @Autowired
    protected ArticleNumeriqueSearchRepository articleNumeriqueSearchRepository;

    /**
     * Correspondance entre une ressource et un document Elasticsearch à indexer
     *
     * @return
     * @throws IOException
     */
    @Test
    public void jacksonArticlePapierTest() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        var articlePapiers = objectMapper.readValue(new File("src/test/resources/json/articles_papiers.json"), new TypeReference<List<ArticlePapier>>() {
        });
        // La relation est bidirectionnelle, il est logique que chaque côté de la relation soit mappé à l'autre,
        // il faut avoir une référence de chaque côté de l'autre côté
        articlePapiers.forEach(articlePapier ->
            {
                Optional<ArticlePapier> existArticlePapier = articlePapierRepository.findByEan(articlePapier.getEan());
                existArticlePapier.ifPresent(papier -> {
                    System.out.println(papier.getPrixTTC());
                    articlePapierService.delete(papier.getId());
                    // articlePapierSearchRepository.deleteById(papier.getId());
                });
                articlePapier.getTvas().forEach(articlePapier::addTva);
                articlePapier.getNiveaus().forEach(articlePapier::addNiveau);
                articlePapier.getDisciplines().forEach(articlePapier::addDiscipline);
                articlePapierService.save(articlePapier);
                // articlePapierSearchRepository.save(articlePapier);
                try {
                    updateArticlePapier(articlePapier);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        );
        System.out.println("FIN");
    }

    @Test
    public void jacksonArticleNumeriqueTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        var articleNumeriques = objectMapper.readValue(new File("src/test/resources/json/articles_numeriques.json"), new TypeReference<List<ArticleNumerique>>() {
        });
        articleNumeriques.forEach(articleNumerique ->
            {
                Optional<ArticleNumerique> existArticleNumerique = articleNumeriqueRepository.findByEan(articleNumerique.getEan());
                existArticleNumerique.ifPresent(numerique -> articleNumeriqueService.delete(numerique.getId()));
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
            }
        );
    }

    public UpdateResponse updateArticlePapier(ArticlePapier articlePapier) throws IOException {

        // Builder
        XContentBuilder builder = createDocumentFromArticlePapier(articlePapier);
        // Request
        UpdateRequest request = new UpdateRequest("articlepapier", articlePapier.getId().toString()).doc(builder);
        // Create or update
        request.upsert(builder);

        // Execute and return
        return elasticsearchClient.update(request, RequestOptions.DEFAULT);
    }


    public void indexTitrePublie(ArticlePapier articlePapier) {
//        // Ressource supprimée
//        if (articlePapier.estSupprime()) {
//            try {
//                deleteTitrepublie(articlePapier);
//            } catch (Exception e) {
//                log.error("Erreur lors de la suppression de la resource par Elasticsearch", e);
//                new IndexerException(e.getMessage());
//            }
//        }

        try {
            updateArticlePapier(articlePapier);
        } catch (Exception e) {
        }

//        // Et optimisation
//        try {
//            refreshTitrePublie();
//        } catch (Exception e) {
//            log.error("Erreur lors du rafraîchissement de l'index", e);
//            throw new IndexerException(e.getMessage());
//        }
    }

    protected static XContentBuilder createDocumentFromArticlePapier(ArticlePapier articlePapier) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
            builder.field(ArticlePapierESEnum.ID.getFieldName(), articlePapier.getId() + "");
            builder.field(ArticlePapierESEnum.EAN.getFieldName(), articlePapier.getEan());
            builder.field(ArticlePapierESEnum.ARK.getFieldName(), articlePapier.getTitre());
            builder.field(ArticlePapierESEnum.TITRE.getFieldName(), articlePapier.getArk());
            builder.field(ArticlePapierESEnum.EDITEUR.getFieldName(), articlePapier.getEditeur());
            builder.field(ArticlePapierESEnum.AUTEUR.getFieldName(), articlePapier.getAuteur());
            builder.field(ArticlePapierESEnum.REF_EDITEUR.getFieldName(), articlePapier.getDescription());
            builder.field(ArticlePapierESEnum.COLLECTION.getFieldName(), articlePapier.getCollection());
            builder.field(ArticlePapierESEnum.DISTRIBUTEUR.getFieldName(), articlePapier.getDistributeur());
            builder.field(ArticlePapierESEnum.URL_COUVERTURE.getFieldName(), articlePapier.getUrlCouverture());
            builder.field(ArticlePapierESEnum.DATE_PARUTION.getFieldName(), articlePapier.getDateParution());
            builder.field(ArticlePapierESEnum.PRIXHT.getFieldName(), articlePapier.getPrixHT());
            builder.field(ArticlePapierESEnum.DESCRIPTION.getFieldName(), articlePapier.getDescription());

            List<Object> objects = new ArrayList<>();
            for (Tva tva : articlePapier.getTvas()) {
                Map<String, Object> innerTva = new HashMap<>();
                innerTva.put(TvaESEnum.TAUX.getFieldName(), tva.getTaux());
                innerTva.put(TvaESEnum.POURCENT.getFieldName(), tva.getPourcent());
                objects.add(innerTva);
            }
            builder.field(ArticlePapierESEnum.TVA.getFieldName(),objects.toArray());
//            builder.field(ArticlePapierESEnum.DISCIPLINE.getFieldName(), articlePapier.getDisciplines());
//            builder.field(ArticlePapierESEnum.NIVEAU.getFieldName(), articlePapier.getNiveaus());
//            builder.field(ArticlePapierESEnum.DISPONIBILITE.getFieldName(),articlePapier.getDisponibilite());
            builder.field(ArticlePapierESEnum.PRIXTTC.getFieldName(), articlePapier.getPrixTTC());
        builder.endObject();
        return builder;
    }
}
