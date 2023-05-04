package fr.openent.moisson.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * Properties specific to Moissoncatalogue.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Indices indices;
    private final Libraire libraire;
    private final Scheduler scheduler;

    public ApplicationProperties(Indices indices, Libraire libraire,
                                 Scheduler scheduler) {
        this.indices = indices;
        this.libraire = libraire;
        this.scheduler = scheduler;
    }

    public Indices getIndices() {
        return indices;
    }

    public Libraire getLibraire() {
        return libraire;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public static class Indices {
        private final String indexArticlePapier;
        private final String indexArticleNumerique;

        public Indices(String indexArticlePapier,
                       String indexArticleNumerique) {
            this.indexArticlePapier = indexArticlePapier;
            this.indexArticleNumerique = indexArticleNumerique;
        }

        public String getIndexArticlePapier() {
            return indexArticlePapier;
        }

        public String getIndexArticleNumerique() {
            return indexArticleNumerique;
        }

    }

    public static class Libraire {
        private final String urlArticlePapier;
        private final String urlArticleNumerique;
        private final String name;

        public Libraire(String urlArticlePapier,
                        String urlArticleNumerique, String name) {
            this.urlArticlePapier = urlArticlePapier;
            this.urlArticleNumerique = urlArticleNumerique;
            this.name = name;
        }

        public String getUrlArticlePapier() {
            return urlArticlePapier;
        }

        public String getUrlArticleNumerique() {
            return urlArticleNumerique;
        }

        public String getName() {
            return name;
        }
    }

    public static class Scheduler {
        private final String cron;

        public Scheduler(String cron) {
            this.cron = cron;
        }

        public String getCron() {
            return cron;
        }
    }
}
