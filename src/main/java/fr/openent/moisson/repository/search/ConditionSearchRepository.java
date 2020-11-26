package fr.openent.moisson.repository.search;

import fr.openent.moisson.domain.Condition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Condition} entity.
 */
public interface ConditionSearchRepository extends ElasticsearchRepository<Condition, Long> {
}
