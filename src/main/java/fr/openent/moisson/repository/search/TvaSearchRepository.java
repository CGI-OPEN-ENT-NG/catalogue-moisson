package fr.openent.moisson.repository.search;

import fr.openent.moisson.domain.Tva;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Tva} entity.
 */
public interface TvaSearchRepository extends ElasticsearchRepository<Tva, Long> {
}
