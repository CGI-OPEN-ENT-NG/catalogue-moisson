package fr.openent.moisson.repository.search;

import fr.openent.moisson.domain.Discipline;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Discipline} entity.
 */
public interface DisciplineSearchRepository extends ElasticsearchRepository<Discipline, Long> {
}
