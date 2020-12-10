package fr.openent.moisson;

import io.github.jhipster.config.DefaultProfileUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * This is a helper Java class that provides an alternative to creating a {@code web.xml}.
 * This will be invoked only when the application is deployed to a Servlet container like Tomcat, JBoss etc.
 */
public class ApplicationWebXml extends SpringBootServletInitializer {
    private static final Logger log = LoggerFactory.getLogger(ApplicationWebXml.class);

    Path realConfigPath;
    private static String contextPath = "";

    /**
     * Déploiement dans un conteneur de servlet de type tomcat,
     * Le profile par défaut est positionné sur production.
     * Le fichier de propriétés est à l'extérieur de l'application, dans le répertoire
     * config situé au meme niveau que le répertoire de contexte
     * Au démarrage il y a des messages d'avertissements voir onStartup
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Profile production activé par défaut
        servletContext.setInitParameter("spring.profiles.active", "production");
        initContextPath(servletContext);
        // Répertoire config situé au meme niveau que le répertoire de contexte
        realConfigPath = Paths.get(servletContext.getRealPath("."), "../config");
        log.info(
            "*************************************************** ATTENTION ****************************************************************");
        if (!Files.exists(realConfigPath)) {
            log.info("Le répertoire de configuration '" + realConfigPath.toAbsolutePath() + "' n'existe pas.                               ");
            log.info("L'application ne va pas démarrer correctement. il faut le créer et y ajouter le fichier de configuration yaml fourni.");
        } else {
            log.info("Le répertoire de configuration '" + realConfigPath.toUri().toString()+ "' est présent.");
        }
        log.info(
            "******************************************************************************************************************************");
        super.onStartup(servletContext);
    }

    public void initContextPath(ServletContext servletContext) throws ServletException {
        contextPath = servletContext.getContextPath();
    }

    public static String getContextPath() {
        return contextPath;
    }

    /**
     * Permet d'exporter les fichiers de propriétés à l'extérieur du WAR
     * @return properties ajoute le répertoire du fichier de configuration :
     * spring.config.location: ../config
     */
    public Properties getProperties() {
        Properties props = new Properties();
        props.put("spring.config.location", realConfigPath.toUri().toString());
        return props;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // set a default to use when no profile is configured.
        DefaultProfileUtil.addDefaultProfile(application.application());
        // Ajout du du répertoire du fichier de propriétés de production avec .properties(getProperties())
        return application.sources(MoissoncatalogueApp.class).properties(getProperties());

    }
}
