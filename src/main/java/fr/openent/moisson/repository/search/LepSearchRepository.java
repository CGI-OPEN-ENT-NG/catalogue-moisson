package fr.openent.moisson.repository.search;

import fr.openent.moisson.domain.Lep;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Lep} entity.
 */
public interface LepSearchRepository extends ElasticsearchRepository<Lep, Long> {
}
