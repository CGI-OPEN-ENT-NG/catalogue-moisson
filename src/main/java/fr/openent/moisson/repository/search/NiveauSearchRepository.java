package fr.openent.moisson.repository.search;

import fr.openent.moisson.domain.Niveau;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Niveau} entity.
 */
public interface NiveauSearchRepository extends ElasticsearchRepository<Niveau, Long> {
}
