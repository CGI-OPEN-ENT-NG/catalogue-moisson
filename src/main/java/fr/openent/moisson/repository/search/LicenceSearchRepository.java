package fr.openent.moisson.repository.search;

import fr.openent.moisson.domain.Licence;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Licence} entity.
 */
public interface LicenceSearchRepository extends ElasticsearchRepository<Licence, Long> {
}
