package fr.openent.moisson.service.jackson
    ;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.openent.moisson.domain.*;
import fr.openent.moisson.repository.ArticleNumeriqueRepository;
import fr.openent.moisson.repository.ArticlePapierRepository;
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
    protected ArticlePapierRepository articlePapierRepository;

    @Autowired
    protected ArticleNumeriqueRepository articleNumeriqueRepository;

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
                    System.out.println(existArticlePapier.get().getPrixTTC());
                    articlePapierRepository.deleteById(existArticlePapier.get().getId());
                }
                    articlePapier.getTvas().forEach(articlePapier::addTva);
                    articlePapier.getNiveaus().forEach(articlePapier::addNiveau);
                    articlePapier.getDisciplines().forEach(articlePapier::addDiscipline);
                    articlePapierRepository.save(articlePapier);
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
                Optional<ArticleNumerique> existArticleNumerique = articleNumeriqueRepository.findByEan(articleNumerique.getEan());
                if (existArticleNumerique.isPresent()) {
                    articleNumeriqueRepository.deleteById(existArticleNumerique.get().getId());
                }
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

                articleNumeriqueRepository.save(articleNumerique);
            }
        );
    }

}
