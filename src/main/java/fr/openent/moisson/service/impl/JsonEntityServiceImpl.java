package fr.openent.moisson.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.openent.moisson.config.ApplicationProperties;
import fr.openent.moisson.domain.ArticleNumerique;
import fr.openent.moisson.domain.ArticlePapier;
import fr.openent.moisson.domain.Lep;
import fr.openent.moisson.domain.Offre;
import fr.openent.moisson.repository.ArticleNumeriqueRepository;
import fr.openent.moisson.repository.ArticlePapierRepository;
import fr.openent.moisson.service.JsonEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class JsonEntityServiceImpl implements JsonEntityService {

    private final Logger log = LoggerFactory.getLogger(JsonEntityServiceImpl.class);

    private final ArticlePapierRepository articlePapierRepository;
    private final ArticleNumeriqueRepository articleNumeriqueRepository;
    private final ApplicationProperties applicationProperties;

    public JsonEntityServiceImpl(ArticlePapierRepository articlePapierRepository,
                                 ArticleNumeriqueRepository articleNumeriqueRepository,
                                 ApplicationProperties applicationProperties) {
        this.articlePapierRepository = articlePapierRepository;
        this.articleNumeriqueRepository = articleNumeriqueRepository;
        this.applicationProperties = applicationProperties;
    }

    String urlArticlePapier;
    String urlArticleNumerique;

    @PostConstruct
    public void initParam() {
        urlArticlePapier = applicationProperties.getLibraire().getUrlArticlePapier();
        urlArticleNumerique = applicationProperties.getLibraire().getUrlArticleNumerique();
    }

    @Override
    public Integer jacksonToArticlePapier() throws IOException {
        AtomicInteger counter = new AtomicInteger(0);
        ObjectMapper objectMapper = new ObjectMapper();
        List<ArticlePapier> articlePapiers;
        if(urlArticlePapier.startsWith("http://") || urlArticlePapier.startsWith("https://")){
            InputStream inputStream = getJsonFromUrl(urlArticlePapier);
            articlePapiers = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } else {
            articlePapiers = objectMapper.readValue(new File("src/test/resources/json/articles_papiers.json"),
                new TypeReference<>() {
                });
        }

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
                counter.getAndSet(counter.get() + 1);
            }
        );
        return counter.get();
    }

    @Override
    public Integer jacksonToArticleNumerique() throws IOException {
        AtomicInteger counter = new AtomicInteger(0);
        ObjectMapper objectMapper = new ObjectMapper();
        List<ArticleNumerique> articleNumeriques;
        if(urlArticleNumerique.startsWith("http://") || urlArticleNumerique.startsWith("https://")){
            InputStream inputStream = getJsonFromUrl(urlArticleNumerique);
            articleNumeriques = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } else {
            articleNumeriques = objectMapper.readValue(new File("src/test/resources/json/articles_numeriques.json"),
                new TypeReference<>() {
                });
        }

        articleNumeriques.forEach(articleNumerique ->
            {
                Optional<ArticleNumerique> existArticleNumerique = articleNumeriqueRepository.findByEan(articleNumerique.getEan());
                existArticleNumerique.ifPresent(numerique -> articleNumeriqueRepository.deleteById(existArticleNumerique.get().getId()));
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
                counter.getAndSet(counter.get() + 1);
            }
        );
        return counter.get();
    }

    InputStream getJsonFromUrl(String urlArticle) throws IOException {
                  // Re-diriger l'utilisateur vers cette URL en passant le PT en paramètre
            URL url = new URL(urlArticle);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            int status = httpURLConnection.getResponseCode();
            log.debug("url : " + url + " et status : " + status);
            log.debug("location : " + httpURLConnection.getHeaderField("location"));
            boolean redirect = false;
            if (status != HttpURLConnection.HTTP_OK) {
                if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM
                    || status == HttpURLConnection.HTTP_SEE_OTHER || status == 307 || status == 308)

                    redirect = true;
            }

            if (redirect) {
                // Obtention de l'URL de redirection dans le header : "location" header field
                String newUrl = httpURLConnection.getHeaderField("Location");

                // Obtention des cookies si nécessaire pour le login
                String cookies = httpURLConnection.getHeaderField("Set-Cookie");

                // Ouverture de la connexion
                httpURLConnection = (HttpURLConnection) new URL(newUrl).openConnection();
                httpURLConnection.setRequestProperty("Cookie", cookies);

                log.debug("Redirect to URL : " + newUrl);

            }
            // Ouverture du flux de lecture
            return httpURLConnection.getInputStream();
    }

    /**
     * @Scheduled(cron = "<minute> <hour> <day-of-month> <month> <day-of-week> <command>")
     * @throws IOException
     */
    @Scheduled(cron = "0 0 4 ? * *")
    void moissonJson() throws IOException {
        LocalTime startTime = LocalTime.now();
        Integer result;
        result = jacksonToArticlePapier();
        result += jacksonToArticleNumerique();
        LocalTime endTime = LocalTime.now();
        Duration duration = Duration.between(startTime, endTime);
        String stringDuration = String.format("%d minutes et %02d secondes %n", duration.toMinutes(), duration.minusMinutes(duration.toMinutes()).getSeconds());
        String message =  result + " articles créés en " + stringDuration;
        log.debug(message);
    }
}
