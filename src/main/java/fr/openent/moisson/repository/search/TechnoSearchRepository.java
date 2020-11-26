package fr.openent.moisson.repository.search;

import fr.openent.moisson.domain.Techno;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Techno} entity.
 */
public interface TechnoSearchRepository extends ElasticsearchRepository<Techno, Long> {
}
