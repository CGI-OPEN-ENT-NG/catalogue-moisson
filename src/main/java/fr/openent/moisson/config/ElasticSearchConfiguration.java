package fr.openent.moisson.config;

import java.io.IOException;

import javax.annotation.PreDestroy;

import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public ElasticsearchOperations elasticsearchTemplate(RestHighLevelClient restHighLevelClient) {
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

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
            .connectedTo(restClientProperties.getUris().get(0))
            .build();
        return RestClients.create(clientConfiguration).rest();

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
