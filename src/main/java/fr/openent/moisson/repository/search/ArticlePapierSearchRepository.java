package fr.openent.moisson.repository.search;

import fr.openent.moisson.domain.ArticlePapier;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ArticlePapier} entity.
 */
public interface ArticlePapierSearchRepository extends ElasticsearchRepository<ArticlePapier, Long> {
}
