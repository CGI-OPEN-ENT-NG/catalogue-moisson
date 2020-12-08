package fr.openent.moisson.service.mapper.jackson
    ;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.openent.moisson.domain.*;
import fr.openent.moisson.repository.ArticlePapierRepository;
import fr.openent.moisson.repository.TvaRepository;
import fr.openent.moisson.service.ArticleNumeriqueService;
import fr.openent.moisson.service.ArticlePapierService;
import fr.openent.moisson.service.TvaService;
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
    protected ArticlePapierService articlePapierService;

    @Autowired
    protected ArticleNumeriqueService articleNumeriqueService;

    @Autowired
    protected ArticlePapierRepository articlePapierRepository;

    @Test
    public void jacksonArticlePapierTest() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        List<ArticlePapier> articlePapiers = objectMapper.readValue(new File("src/test/resources/json/articles_papiers.json"), new TypeReference<List<ArticlePapier>>() {
        });
        // La relation est bidirectionnelle, il est logique que chaque côté de la relation soit mappé à l'autre,
        // il faut avoir une référence de chaque côté de l'autre côté
        articlePapiers.forEach(articlePapier ->
            {
                Optional<ArticlePapier> existArticlePapier = articlePapierRepository.findByEan(articlePapier.getEan());
                if (existArticlePapier.isPresent()) {
                    System.out.println(existArticlePapier.get().getTvas());
                    existArticlePapier.get().removeTvas();
                    // articlePapierService.save(existArticlePapier.get());
                    articlePapier.getTvas().forEach(existArticlePapier.get()::addTva);
                    articlePapierService.save(existArticlePapier.get());
                } else {
                    articlePapier.getTvas().forEach(articlePapier::addTva);
                    articlePapierRepository.save(articlePapier);
                }
            }
        );
    }

    @Test
    public void jacksonArticleNumeriqueTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ArticleNumerique> articleNumeriques = objectMapper.readValue(new File("src/test/resources/json/articles_numeriques.json"), new TypeReference<List<ArticleNumerique>>() {
        });
        articleNumeriques.forEach(articleNumerique ->
            {
                for (Offre offre : articleNumerique.getOffres()) {
                    for (Lep lep : offre.getLeps()) {
                        lep.getConditions().forEach(lep::addCondition);
                        offre.addLep(lep);
                    }
                    offre.getTvas().forEach(offre::addTva);
                    // offre.setLicence(offre.getLicence());
                }
                articleNumerique.getNiveaus().forEach(articleNumerique::addNiveau);
                articleNumerique.getDisciplines().forEach(articleNumerique::addDiscipline);
                //articleNumerique.setDisponibilite(articleNumerique.getDisponibilite());

                articleNumeriqueService.save(articleNumerique);
            }
        );
    }

}
