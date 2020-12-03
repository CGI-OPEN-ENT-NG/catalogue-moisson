package fr.openent.moisson.repository.search;

import fr.openent.moisson.domain.Disponibilite;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Disponibilite} entity.
 */
public interface DisponibiliteSearchRepository extends ElasticsearchRepository<Disponibilite, Long> {
}
