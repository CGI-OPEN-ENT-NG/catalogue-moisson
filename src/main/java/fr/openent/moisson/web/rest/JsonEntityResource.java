package fr.openent.moisson.web.rest;

import fr.openent.moisson.service.JsonEntityService;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.stream.Stream;

/**
 * REST controller for managing {@link fr.openent.moisson.domain.Lep}.
 */
@RestController
@RequestMapping("/api")
public class JsonEntityResource {

    private final Logger log = LoggerFactory.getLogger(JsonEntityResource.class);

    private static final String ENTITY_NAME = "jsonEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JsonEntityService jsonEntityService;

    public JsonEntityResource(JsonEntityService jsonEntityService) {
        this.jsonEntityService = jsonEntityService;
    }

    /**
     * {@code POST  /json} : Persist json file in database.
     *
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lepDTO, or with status {@code 400 (Bad Request)} if the lep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/json/{typeArticle}")
    public ResponseEntity<String> createEnties(@PathVariable String typeArticle) throws URISyntaxException, IOException {
        log.debug("REST request to save Json File for type : {}", typeArticle);
        LocalTime startTime = LocalTime.now();
        Integer result = 0;
        if (Stream.of("pap", "num", "all").anyMatch(s -> s.equalsIgnoreCase(typeArticle)))
        {
            switch (typeArticle.toLowerCase()) {
                case "pap":
                    result = jsonEntityService.jacksonToArticlePapier();
                    break;

                case "num":
                    result = jsonEntityService.jacksonToArticleNumerique();
                    break;

                case "all": {
                    result = jsonEntityService.jacksonToArticlePapier();
                    result += jsonEntityService.jacksonToArticleNumerique();
                }
                break;
                default:
                    throw new IllegalStateException("Unexpected value: " + typeArticle.toLowerCase());
            }
        }
        LocalTime endTime = LocalTime.now();
        Duration duration = Duration.between(startTime, endTime);
        String stringDuration = String.format("%d minutes et %02d secondes %n", duration.toMinutes(), duration.minusMinutes(duration.toMinutes()).getSeconds());
        String message =  result + " articles créés en " + stringDuration;
        return ResponseEntity.created(new URI("/api/json/" + typeArticle))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, message))
            .body(message);
    }

}
