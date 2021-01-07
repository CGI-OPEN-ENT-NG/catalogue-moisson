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
    private final Libraire libraire;

    private final Scheduler scheduler;

    public ApplicationProperties(Libraire libraire,
                                 Scheduler scheduler) {
        this.libraire = libraire;
        this.scheduler = scheduler;
    }

    public static class Libraire{
        private final String urlArticlePapier;
        private final String urlArticleNumerique;

        public Libraire(String urlArticlePapier,
                             String urlArticleNumerique) {
            this.urlArticlePapier = urlArticlePapier;
            this.urlArticleNumerique = urlArticleNumerique;
        }

        public String getUrlArticlePapier() {
            return urlArticlePapier;
        }

        public String getUrlArticleNumerique() {
            return urlArticleNumerique;
        }
    }

    public Libraire getLibraire() {
        return libraire;
    }

    public static class Scheduler{
        private final String cron;
        public Scheduler(String cron) {
            this.cron = cron;
        }

        public String getCron() {
            return cron;
        }
    }

    public Scheduler getScheduler() {
        return scheduler;
    }
}
