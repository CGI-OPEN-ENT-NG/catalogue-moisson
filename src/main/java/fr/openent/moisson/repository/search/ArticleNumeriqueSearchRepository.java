package fr.openent.moisson.repository.search;

import fr.openent.moisson.domain.ArticleNumerique;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ArticleNumerique} entity.
 */
public interface ArticleNumeriqueSearchRepository extends ElasticsearchRepository<ArticleNumerique, Long> {
}
