package fr.openent.moisson.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import javax.annotation.PreDestroy;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import static javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory;
import static javax.net.ssl.SSLContext.getInstance;

@Configuration
@EnableConfigurationProperties(ElasticsearchProperties.class)

public class ElasticSearchConfiguration extends AbstractElasticsearchConfiguration {
    private final Logger logger = LoggerFactory.getLogger(ElasticSearchConfiguration.class);

	private final RestClientProperties restClientProperties;

	public ElasticSearchConfiguration(RestClientProperties restClientProperties) {
		this.restClientProperties = restClientProperties;
	}

    @Bean
    @Primary
    public ElasticsearchOperations elasticsearchTemplate(@Qualifier("elasticsearchClient") RestHighLevelClient restHighLevelClient) {
        return new ElasticsearchRestTemplate(
            restHighLevelClient);
    }

	/**
	 * Si le serveur n'est pas disponible, l'application n'est pas plantée
	 * La classe de base AbstractElasticsearchConfiguration fournit déjà
	 * un bean elasticsearchTemplate (alias de elasticsearchOperations ).
	 */
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        // Vérifier si les paramètres de connexion indiquent un compte d'accès.
        // Si il y a un compte, la connexion doit se faire en SSL insecure
        ClientConfiguration clientConfiguration;
        if ((restClientProperties.getUsername() != null) && (restClientProperties.getPassword() != null)) {
            try {
                var sslContext = getInstance("SSL");
                sslContext.init(null,
                    getTrustAllCerts(),
                    new SecureRandom());
                setDefaultSSLSocketFactory(sslContext.getSocketFactory());
                // Pas de vérification de noms de domaine
                var hostnameVerifier = new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostName, SSLSession sslSession) {
                        return true;
                    }
                };
                clientConfiguration = ClientConfiguration.builder().connectedTo(restClientProperties.getUris().get(0))
                    .usingSsl(sslContext, hostnameVerifier)
                    .withBasicAuth(restClientProperties.getUsername(), restClientProperties.getPassword()).build();
            } catch (Exception e) {
                logger.error("Impossibilité de se connecter en mode sécurisé à ElasticSearch)");
                logger.error("Paramètre de connexion : \nuser : " + restClientProperties.getUsername() + " - \nadmin : "
                    + restClientProperties.getPassword() + "\n uris : " + restClientProperties.getUris());
                e.printStackTrace();
                clientConfiguration = ClientConfiguration.builder().connectedTo(restClientProperties.getUris().get(0))
                    .build();
            }
        } else {
            clientConfiguration = ClientConfiguration.builder().connectedTo(restClientProperties.getUris().get(0))
                .build();
        }
        return RestClients.create(clientConfiguration).rest();
    }

    private TrustManager[] getTrustAllCerts() {
        return new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
            // Client
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
           // Server
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };
    }

    @PreDestroy
    public void cleanUp() {
        try {
            logger.info("Fermeture du client Rest ES");
            this.elasticsearchClient().close();
        }
        catch (IOException ioe) {
            logger.error("Problème est apparu pendant fermeture du client Rest ES", ioe);
        }
    }

}
