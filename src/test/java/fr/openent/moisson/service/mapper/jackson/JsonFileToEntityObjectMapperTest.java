package fr.openent.moisson.service.mapper.jackson
    ;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.openent.moisson.domain.ArticleNumerique;
import fr.openent.moisson.domain.ArticlePapier;
import fr.openent.moisson.domain.Tva;
import fr.openent.moisson.service.ArticlePapierService;
import fr.openent.moisson.service.dto.ArticleNumeriqueDTO;
import fr.openent.moisson.service.dto.ArticlePapierDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JsonFileToEntityObjectMapperTest {

    @Autowired
    protected ArticlePapierService articlePapierService;

    @Test
    public void jacksonArticlePapierTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        List<ArticlePapier> articlePapiers = objectMapper.readValue(new File("src/test/resources/json/articles_papiers.json"), new TypeReference<List<ArticlePapier>>() { });
        // articlePapiers.forEach(System.out::println);
        // La relation est bidirectionnelle, il est logique que chaque côté de la relation soit mappé à l'autre,
        // il fautt avoir une référence de chaque côté de l'autre côté
        articlePapiers.forEach(ap ->
            {
                ap.getTvas().forEach(ap::addTva);
                articlePapierService.save(ap);
            }
        );
       //  System.out.println(articlePapiers);

    }
    @Test
    public void jacksonArticleNumeriqueTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArticleNumerique articleNumerique = objectMapper.readValue(new File("src/test/resources/json/articles_numeriques.json"), ArticleNumerique.class);
    }

}
